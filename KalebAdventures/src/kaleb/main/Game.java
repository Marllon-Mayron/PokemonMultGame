package kaleb.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import kaleb.controller.EventController;
import kaleb.controller.ItemController;
import kaleb.controller.PcController;
import kaleb.controller.StoreController;
import kaleb.controller.TradeController;

//Importa��o dos Packages

import kaleb.entities.Entity;
import kaleb.entities.Locale;
import kaleb.entities.Player;
import kaleb.entities.Pokemon;
import kaleb.entities.SlotPc;
import kaleb.graphics.Spritesheet;
import kaleb.graphics.UI;
import kaleb.itens.BattleItem;
import kaleb.itens.Medicine;
import kaleb.itens.Pokeball;
import kaleb.world.Camera;
import kaleb.world.LevelsConfigs;
import kaleb.world.PokeGenerator;
import pokeData.Pokedex;
import pokeData.Slot;

public class Game extends Canvas implements Runnable, KeyListener,MouseListener,MouseMotionListener {

	private static final long serialVersionUID = 1L;
	// Variables
	//Janela e Run Game
	public static JFrame frame;
	private boolean isRunning = true;
	private Thread thread;
	public static final int WIDTH = 240, HEIGHT = 160, SCALE = 3;
	
	//Imagens e Gr�ficos
	private BufferedImage image;
	public static UI ui;
	private Graphics g;
	//Entities
	public static List<Entity> entities;
	public static List<LevelsConfigs> lvlConfig;
	public static List<Slot> slotList;
	public static List<SlotPc> slotPcList;
	public static List<Pokemon> pokeList, waveList;
	public static List<PokeGenerator> generateList;
	public static Spritesheet spritesheet;
	public static Player player;
	public static Pokedex pokedex = new Pokedex();
	public static int currentLvl = 0;
	public static Random random;
	public static Config configs = new Config();
	public static int FPS = 0;
	public static String gameState = "tutorial";
	public static int tutorialSteps = 0;
	public static PcController pc;
	public static StoreController storeController = new StoreController();
	public static ItemController itemController = new ItemController();
	public static EventController eventController = new EventController();
	public static TradeController tradeController = new TradeController();
	//public static Locale ltst;
	/***/
	public static Timer timer;
	private static int wait = 0;
	public static void timer() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
            	wait++;
            	if(wait == 1) {
            		Game.configs.userPerformance();
            	}           	
            	if(wait >= 2) {
            		player.nextGameLevel();
            		gameState = "catch";
            		ui.pcView = "pc";
            		wait = 0;
               	 	timer.cancel();
            	}
            	 
            }
        }, 0, (long) (configs.state2Time * 1000)); // Inicia imediatamente e repete a cada 1000 milissegundos (1 segundo)
    }
	// Construtor
	public Game() {
		random = new Random();
		this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		initFrame();
		// Inicializando Objetos
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		entities = new ArrayList<Entity>();
		lvlConfig = new ArrayList<LevelsConfigs>();
		slotList = new ArrayList<Slot>();
		slotPcList = new ArrayList<SlotPc>();
		pokeList = new ArrayList<Pokemon>();
		waveList = new ArrayList<Pokemon>();
		generateList = new ArrayList<PokeGenerator>();
		// LEVELS CONFIGS =============================================
		LevelsConfigs lvl0 = new LevelsConfigs(0, 1, 0.1, 5, 5);
		LevelsConfigs lvl1 = new LevelsConfigs(1, 12, 2.5, 3, 5);
		LevelsConfigs lvl2 = new LevelsConfigs(2, 21, 3.5, 5, 8);
		LevelsConfigs lvl3 = new LevelsConfigs(3, 25, 3.5, 8, 12);
		LevelsConfigs lvl4 = new LevelsConfigs(4, 25, 4, 12, 16);
		LevelsConfigs lvl5 = new LevelsConfigs(5, 31, 4, 16, 20);
		LevelsConfigs lvl6 = new LevelsConfigs(6, 31, 5, 18, 22);
		lvlConfig.add(lvl0);
		lvlConfig.add(lvl1);
		lvlConfig.add(lvl2);
		lvlConfig.add(lvl3);
		lvlConfig.add(lvl4);
		lvlConfig.add(lvl5);
		lvlConfig.add(lvl6);
		//==============================================================
		
		ui = new UI();
		player = new Player(0, 0, 32, 32, null);
		
		for(int i = 0; i < 6; i++) {
			Slot slot = new Slot(i+1, null);
			slotList.add(slot);
		}
		
		entities.add(player);
		pc = new PcController(0,0,0,0,null);
		// Para que os eventos de teclado funcionem
		addKeyListener(this);
		addMouseMotionListener(this);
		addMouseListener(this);
	}

	// Cria��o da Janela
	public void initFrame() {
		frame = new JFrame("Pokemon");
		frame.add(this);
		frame.setResizable(false);// Usu�rio n�o ir� ajustar janela
		frame.pack();
		frame.setLocationRelativeTo(null);// Janela inicializa no centro
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// Fechar o programa por completo
		frame.setVisible(true);// Dizer que estar� vis�vel
	}

	// Threads
	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}

	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}

	public void tick() {
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			if (e instanceof Player) {
				player.tick();
			}
			e.tick();
		}
		for (int i = 0; i < slotPcList.size(); i++) {
			Entity e = slotPcList.get(i);
			e.tick();
		}
		for (int i = 0; i < storeController.slotStoreList.size(); i++) {
			Entity e = storeController.slotStoreList.get(i);
			e.tick();
		}
		for (int i = 0; i < tradeController.slotList.size(); i++) {
			Entity e = tradeController.slotList.get(i);
			e.tick();
		}
	}

	// O que ser� mostrado em tela
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();// Sequ�ncia de buffer para otimizar a renderiza��o, lidando com
														// performace gr�fica
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		g = image.getGraphics();// Renderizar imagens na tela
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);


		g.dispose();// Limpar dados de imagem n�o usados		
		g = bs.getDrawGraphics();
		
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		
		lvlConfig.get(0).render(g);
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			if(e.equals(player)) {
				continue;
			}
			e.renderStr(g);
		}
		lvlConfig.get(0).render2(g);
		
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			if(e.equals(player)) {
				continue;
			}
			e.render(g);
		}
		ui.render(g);
		tradeController.render(g);
		for (int i = 0; i < slotPcList.size(); i++) {
			Entity e = slotPcList.get(i);
			e.renderStr(g);
		}
		for (int i = 0; i < storeController.slotStoreList.size(); i++) {
			Entity e = storeController.slotStoreList.get(i);
			e.renderStr(g);
		}
		for (int i = 0; i < tradeController.slotList.size(); i++) {
			Entity e = tradeController.slotList.get(i);
			e.renderStr(g);
		}
		//====================================
		
		player.render(g);
		g.setColor(Color.white);
		
		//====================================
		bs.show();
	}

	// Controle de FPS
	public void run() {
		// Variables
		long lastTime = System.nanoTime();// Usa o tempo atual do computador em nano segundos, bem mais preciso
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;// Calculo exato de Ticks
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		// Ruuner Game
		while (isRunning == true) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;

			if (delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}

			if (System.currentTimeMillis() - timer >= 1000) {
				FPS = frames;
				//System.out.println(frames);
				frames = 0;
				timer += 1000;
			}
		}

		stop(); // Garante que todas as Threads relacionadas ao computador foram terminadas,
				// para garantir performance.

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {	
		
		if (e.getKeyCode() == KeyEvent.VK_Z) {
			if(configs.debugMode == true) {
				configs.debugMode = false;
			}else {
				configs.debugMode = true;
			}
		}
		if(configs.debugMode) {
			if (e.getKeyCode() == KeyEvent.VK_E) {
				if(configs.activeDesc) {
					configs.activeDesc = false;
				}else {
					configs.activeDesc = true;
				}
				
			}if (e.getKeyCode() == KeyEvent.VK_R) {
				if(configs.activeRange) {
					configs.activeRange = false;
				}else {
					configs.activeRange = true;
				}
				
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			if(gameState.equalsIgnoreCase("catch")) {
				putSlotItem();
			}
			
			if(tutorialSteps == 0) {
				tutorialSteps++;					
			}else if(tutorialSteps == 1) {
				int i = 0;
				while(i < 2) {
					Pokemon inicial;
					inicial = new Pokemon(0, 0, 5, 5, null, false, ui.startSelect, 5, false);
					inicial.inMap = false;
					pokeList.add(inicial);
					slotList.get(i).setPokemon(inicial);
					slotPcList.get(i).pokemon = inicial;
					slotPcList.get(i).sprite = pokedex.getSprite(inicial.id, "front", inicial.isShiny);
					i++;
				}
				int inicialOposto = 1;
				if(ui.startSelect == 1) {
					inicialOposto = 4;
				}else if(ui.startSelect == 4) {
					inicialOposto = 7;
				}
				lvlConfig.get(currentLvl).routePokemonBossList[0][0] = inicialOposto+"-200";
				lvlConfig.get(currentLvl).generateLocale();
				lvlConfig.get(currentLvl).generateLvl();
				gameState = "catch";
				Pokeball pokeball = new Pokeball(0, null, null, 0, null, 8);
				Pokeball greatball = new Pokeball(1, null, null, 0, null, 2);
				Medicine potion = new Medicine(0, null, null, 0, null, 2);
				BattleItem bItem = new BattleItem(0, null, null, 0, null, 4);
				Player.pokeballItemList.add(pokeball);
				Player.pokeballItemList.add(greatball);
				Player.medicineItemList.add(potion);
				Player.battleItemList.add(bItem);
				tutorialSteps++;	
				
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_Q) {
			//configs.activeDesc = true;
		}if (e.getKeyCode() == KeyEvent.VK_H) {
			if(configs.activeHud == true) {
				configs.activeHud = false;
			}else {
				configs.activeHud = true;
			}
		}
		//SLOT ITEM NAVEGATION
		if(player.spriteDesc == "") {
			if (e.getKeyCode() == KeyEvent.VK_A) {
				if(ui.nColumn > 0) {
					ui.nColumn--;
				}
			}else if (e.getKeyCode() == KeyEvent.VK_D) {
				if(ui.nColumn < player.listSize -1) {
					ui.nColumn++;
				}
			}
			
			if (e.getKeyCode() == KeyEvent.VK_W) {
				
				ui.nColumn = 0;
				ui.nRow++;
				
			}else if (e.getKeyCode() == KeyEvent.VK_S) {
				if(ui.nRow > 0) {
					ui.nColumn = 0;
					ui.nRow--;
				}
			}
		}
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			player.sprite = null;
			player.spriteDesc = ""; 
			player.pokemonDragged = null;
		}
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		player.draged = true;
		//ATUALIZAR POSIÇÃO
		updatePositions(e);
		if(Game.gameState.equalsIgnoreCase("catch")) {
			//ARRASTAR DO TIME PRINCIPAL PRO MAPA
			draggingForMap();
			//ARRASTAR DO SLOT DE ITEM
			if(player.sprite == null && player.x  >= (Game.WIDTH*89/100) && player.x <= (Game.WIDTH*87/100)  + 12) {
				if(player.y >= (Game.HEIGHT*83/100)  && player.y <= (Game.HEIGHT*83/100)  + 12) {
					putSlotItem();
				}
			}
			//REPOSICIONAR POKEMONS NO MAPA
			repositionPokemon();
		}
		
		
		
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		updatePositions(e);
		
		//VISUALIZAÇÃO DE SLOTS SELECIONADOS
		for(int i = 0; i < 6; i++) {
			if(player.x *3 >= (WIDTH*14/100) * SCALE + (WIDTH*((i)*10)/100)*SCALE + (i+1)*(WIDTH*2/100)*SCALE && player.x * SCALE < (WIDTH*14/100) * SCALE + (WIDTH*((i)*10)/100)*SCALE + (i+1)*(WIDTH*2/100)*SCALE + (WIDTH*10/100)*SCALE) {
				if(player.y * SCALE >= ((Game.HEIGHT*81/100) *Game.SCALE)+2 && player.y * SCALE < ((Game.HEIGHT*81/100) *Game.SCALE)+2 + 70) {
					slotList.get(i).isSelected = true;
					break;
				}else {
					slotList.get(i).isSelected = false;
				}
			}else {
				slotList.get(i).isSelected = false;
			}	
		}
		
		for(int i = 0; i < storeController.slotStoreList.size(); i++) {
			if(Entity.isColidding2(player, storeController.slotStoreList.get(i))) {
				storeController.slotStoreList.get(i).selected = true;
				
			}else {
				storeController.slotStoreList.get(i).selected = false;
			}
		}
	
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(gameState.equalsIgnoreCase("user_view")) {
			bntsClicks();
			
			if(ui.pcView.equalsIgnoreCase("pc")) {
				//REMOVER OS POKEMONS DO TIME PRINCIPAL
				mainTimeRemove();
			}else {
				//LOJA
				
			}
			
		}
		//RETIRAR POKEMON DO LOCAL
		bringBackPokemon();
		
	}

	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		player.x = (e.getX()/SCALE);
		player.y = (e.getY()/SCALE);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		player.draged = false;
		
		if(Game.gameState.equalsIgnoreCase("tutorial")) {
			
		}else if(Game.gameState.equalsIgnoreCase("catch")) {
			//SOLTAR POKEMON DO SLOT PARA O MAP
			putInMap();
			//ARASTAR POKEMONS QUE JA ESTÃO NO MAPA
			draggedInMap();	
			//VERIFICAÇÕES ENVOLVENDO POKEMONS
			mouseReleaseInPokemon();
			//SETAS DE ITENS
			slotItemNavegation();
		}else if(Game.gameState.equalsIgnoreCase("user_view")) {
			if(ui.pcView.equalsIgnoreCase("pc")) {
				//ESCOLHER POKEMON DO PC
				pcSlotClick();
				//DELETAR POKEMON
				deletePokemon();
				//PASSAR PAGINAS NO PC
				pcPageNavegation();
				//SETAS DE ITENS
				slotItemNavegation();
				//TIRAR POKEMON DO PC E COLOCAR NO TIME PRINCIPAL
				mainTimeChange();
			}else if(ui.pcView.equalsIgnoreCase("store")) {
				//BTN COMPRAR
				btnBuy();
				//ESCOLHER ITEM NA LOJA
				storeSlotClick();
				//REGERAR ITENS NA LOJA
				diceStore();
				//SETAS DE ITENS
				slotItemNavegation();
			}else if(ui.pcView.equalsIgnoreCase("trade")) {
				if(tradeController.offertOn){
					//VISUALIZA OPÇÕES POSSIVEIS PARA AS TROCAS
					viewTradeOptions();
					
					//FAZER A TROCA
					btnTrade();
					
					//ESCOLHER PARA A TROCA
					choiceTrade();
		
				}
						}
			
		}
		
		
		
		player.sprite = null;
		player.spriteDesc = ""; 
		player.pokemonDragged = null;
	}



	
	private void choiceTrade() {
		if(tradeController.slotList.size() > 0) {
			for(int i = 0; i < tradeController.slotList.size(); i++) {
				if(tradeController.slotList.get(i).pokemon != null){
					if(player.x > (tradeController.slotList.get(i).x)/SCALE && player.x < (tradeController.slotList.get(i).x)/SCALE + (tradeController.slotList.get(i).getWidth())/SCALE) {
						if(player.y > (tradeController.slotList.get(i).y)/SCALE && player.y < (tradeController.slotList.get(i).y)/SCALE + (tradeController.slotList.get(i).getHeight())/SCALE) {
							
							for(int j = 0; j < tradeController.slotList.size(); j++) {
								tradeController.slotList.get(j).selected = false;
							}
							tradeController.chosen = tradeController.sameOfferList.get(i);
							tradeController.slotList.get(i).selected = true;
							
						}
					}
				 }
			}
		}
		// TODO Auto-generated method stub
		
	}
	private void btnTrade() {
		// TODO Auto-generated method stub
		if(player.x >= (int)(Game.WIDTH * 9.5 / 100) && player.x <= (int)(Game.WIDTH * 9.5 / 100) + (Game.WIDTH * 15 / 100)) {
			if(player.y >= Game.HEIGHT * 62 / 100 && player.y <= Game.HEIGHT * 62 / 100 + 10) {
				for(int i = 0; i < tradeController.slotList.size(); i++) {
					for(int j = 0; j < tradeController.slotList.size(); j++) {
						if(tradeController.slotList.get(j).selected == true){
							if(tradeController.slotList.get(i).selected) {
								 tradeController.tradeAccept(tradeController.slotList.get(i).pokemon);
								 break;
							}
						}
					}			
				}
			}
		}
	}
	private void viewTradeOptions() {
		if(Game.player.x > (int)(Game.WIDTH * 4.3 / 100) && player.x < (int)(Game.WIDTH * 4.3 / 100) +  (Game.WIDTH * 10 / 100)) {
			if(player.y > Game.HEIGHT * 40 / 100 && player.y < Game.HEIGHT * 40 / 100 + 70/3) {
				tradeController.populateSameOffer();
			}
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	//METODOS DE MOUSE DRAGGED
	private void repositionPokemon() {
		if(player.spriteDesc.equalsIgnoreCase("")) {
			for(int i = 0; i < entities.size(); i++) {				
				if(entities.get(i) instanceof Locale) {	
					Locale locale = (Locale) entities.get(i);
					if(locale.pokemon == null) {
						continue;
					}
					if(Entity.isColiddingPlayer(player, locale)) {
						player.spriteDesc = "pokemon";
						player.sprite = pokedex.getSprite(locale.pokemon.id, "front", locale.pokemon.isShiny);
						player.pokemonDragged = locale.pokemon;
					}
				}
			}
		}
	}
	private void draggingForMap() {
		for(int i = 0; i < 6; i++) {
			//CHECAR SE O SLOT ESTÁ LIVRE
			if(slotList.get(i).getSlotStatus().equalsIgnoreCase("free") && slotList.get(i).getPokemon() != null) {
				//CHECAR SE O MOUSE JA ESTÁ COM SPRITE, E SE ELE ESTÁ NO LUGAR CORRETO
				if(player.sprite == null && player.x * SCALE >= (WIDTH*14/100) * SCALE + (WIDTH*((i)*10)/100)*SCALE + (i+1)*(WIDTH*2/100)*SCALE && player.x * SCALE < (WIDTH*14/100) * SCALE + (WIDTH*((i)*10)/100)*SCALE + (i+1)*(WIDTH*2/100)*SCALE + (WIDTH*10/100)*SCALE) {
					if(player.y * SCALE >= ((Game.HEIGHT*81/100) *Game.SCALE)+2 && player.y * SCALE < ((Game.HEIGHT*81/100) *Game.SCALE)+2 + 70) {
						//MUDAR SPRITE DO MOUSE
						player.sprite = pokedex.getSprite(slotList.get(i).getPokemon().id, "Back", slotList.get(i).getPokemon().isShiny);
						player.spriteDesc = "pokemon"; 
						break;
					}				
				}	
			}			
		}
	}
	private void putSlotItem() {
		//MUDAR SPRITE DO MOUSE				
		if(ui.nRow == 0) {
			if(Player.pokeballItemList.get(ui.nColumn).current > 0) {
				player.sprite = Player.pokeballItemList.get(ui.nColumn).sprite;
				player.spriteDesc = "pokeball"; 
			}	
		}else if(ui.nRow == 1) {
			if(Player.medicineItemList.get(ui.nColumn).current > 0) {
				player.sprite = Player.medicineItemList.get(ui.nColumn).sprite;
				player.spriteDesc = "medicine";
			} 
		}else if(ui.nRow == 2) {
			if(Player.battleItemList.get(ui.nColumn).current > 0) {
				player.sprite = Player.battleItemList.get(ui.nColumn).sprite;
				player.spriteDesc = "battleItem";
			} 
		}else if(ui.nRow == 3) {
			if(Player.evolueItemList.get(ui.nColumn).current > 0) {
				player.sprite = Player.evolueItemList.get(ui.nColumn).sprite;
				player.spriteDesc = "evolueItem";
			} 
		}		
				
		
	}
	//METODOS DE MOUSE MOVED
	private void updatePositions(MouseEvent e) {
		player.x = (e.getX()/SCALE);
		player.y = (e.getY()/SCALE);
		Camera.x = (int) Camera.clamp(player.x - (Game.lvlConfig.get(0).mapSize[Game.currentLvl][0] + Game.lvlConfig.get(0).mapSizeOffset[Game.currentLvl][0])/ Game.WIDTH , 0,((Game.lvlConfig.get(0).mapSize[Game.currentLvl][0] + Game.lvlConfig.get(0).mapSizeOffset[Game.currentLvl][0] )- Game.WIDTH )*3);
		Camera.y = (int) Camera.clamp(player.y - (Game.lvlConfig.get(0).mapSize[Game.currentLvl][1] + Game.lvlConfig.get(0).mapSizeOffset[Game.currentLvl][1])/ Game.HEIGHT, 0,((Game.lvlConfig.get(0).mapSize[Game.currentLvl][1] + Game.lvlConfig.get(0).mapSizeOffset[Game.currentLvl][1] )- Game.HEIGHT)*3);

	}
	//METODOS DE MOUSE CLICKED
	private void mainTimeRemove() {
		for(int i = 0; i < 6; i++) {
			if(slotList.get(i).pokemon != null) {
				if((player.x >= (Game.WIDTH * 14 / 100) + (Game.WIDTH * ((i) * 10) / 100)+ (i + 1) * (Game.WIDTH * 2 / 100) && player.x <= (Game.WIDTH * 14 / 100)+ (Game.WIDTH * ((i) * 10) / 100)+ (i + 1) * (Game.WIDTH * 2 / 100) + (Game.WIDTH * 10 / 100))&&(player.y >= ((Game.HEIGHT * 81 / 100)) + 2 && player.y <= ((Game.HEIGHT * 81 / 100)) + 72)){
					for(int i2 = 0; i2 < slotPcList.size(); i2++) {
						if(slotPcList.get(i2).pokemon == slotList.get(i).pokemon) {						
							slotPcList.get(i2).slotPrincipal = false;
							break;
						}else {
							continue;
						}
					}
					//CONTAGEM PRA SABER SE TEM PELO MENOS 1 POKEMON NA SUA EQUIPE
					int nTotal = 0;
					for(int i3 = 0; i3 < 6; i3++) {
						if(slotList.get(i3).pokemon != null) {
							nTotal++;
						}
					}
					if(nTotal > 1) {
						slotList.get(i).pokemon = null;
					}
				}
			}
		}
	}
	private void bringBackPokemon() {
		if(player.spriteDesc.equalsIgnoreCase("")) {
			for(int i = 0; i < Game.entities.size(); i++) {				
				if(Game.entities.get(i) instanceof Locale ){
					Locale l = (Locale) Game.entities.get(i);
					if(Entity.isColiddingPlayer(player, l)) {	
						if(l.getPokemon() != null) {
							for(int i2 = 0; i2 < Game.entities.size(); i2++) {
								if(Game.entities.get(i2) instanceof Pokemon) {
									if(Game.entities.get(i2) == l.getPokemon()) {
										Pokemon p = (Pokemon) Game.entities.get(i2);
										for(int i3 = 0; i3 < Game.slotList.size(); i3++) {
											if(slotList.get(i3).getPokemon() == l.getPokemon()) {
												slotList.get(i3).setSlotStatus("free");												
												p.target = null;
												p.inMap = false;
												if(p.buffed) {
													p.aplicateBuffLocale(false);
												}												
												p.resetExit();
												l.setPokemon(null);
												entities.remove(p);
											}
										}
									}
								}
							}
						}					
					}					
				}
			}
		}
	}
	//METODOS DE MOUSE RELEASE
	private void bntsClicks() {
		if(player.x >= Game.WIDTH * 25 / 100 && player.x <= WIDTH * Game.WIDTH * 25 / 100 + Game.WIDTH * 10 / 100) {
			if(player.y >= HEIGHT * 2 / 100 && player.y <= HEIGHT * 2 / 100 + HEIGHT * 8 / 100) {
				ui.pcView = "pc";
			}
		}
		if(player.x >= WIDTH * 45 / 100 && player.x <= WIDTH * 45 / 100 + Game.WIDTH * 10 / 100) {
			if(player.y >= HEIGHT * 2 / 100 && player.y <= HEIGHT * 2 / 100 + HEIGHT * 8 / 100) {
				ui.pcView = "store";
				storeController.generateItem();
			}
		}
		if(player.x >= WIDTH * 65 / 100 && player.x <= WIDTH * 65 / 100 + Game.WIDTH * 10 / 100) {
			if(player.y >= HEIGHT * 2 / 100 && player.y <= HEIGHT * 2 / 100 + HEIGHT * 8 / 100) {
				ui.pcView = "trade";
				storeController.generateItem();
			}
		}
		
	}
	private void mainTimeChange() {
		for(int i = 0; i < 6; i++) {
			if(slotList.get(i).pokemon == null && player.pokemonDragged != null) {
				if((player.x >= (WIDTH * 14 / 100) + (WIDTH * ((i) * 10) / 100)+ (i + 1) * (WIDTH * 2 / 100) && player.x <= (WIDTH * 14 / 100)+ (WIDTH * ((i) * 10) / 100)+ (i + 1) * (WIDTH * 2 / 100) + (WIDTH * 10 / 100))&&(player.y >= ((HEIGHT * 81 / 100)) + 2 && player.y <= ((HEIGHT * 81 / 100)) + 72)){					
					slotList.get(i).pokemon = player.pokemonDragged;
				}
			}
		}
	}
	private void btnBuy() {
		if(player.x >= (int)(Game.WIDTH * 82.5 / 100) && player.x <= (int)(Game.WIDTH * 82.5 / 100) + 23) {
			if(player.y >= Game.HEIGHT * 59 / 100 && player.y <= Game.HEIGHT * 59 / 100 + 7) {
				if(player.money >= ui.itemDetails.value) {
					itemController.typifyItem(ui.itemDetails);
					player.money -= ui.itemDetails.value;
				}
			}
		}
	}
	private void diceStore() {
		if(ui.pcView.equalsIgnoreCase("store")) {
			if(player.x >= (Game.WIDTH * 50 / 100) - 5 && player.x <= (Game.WIDTH * 50 / 100) - 5 + 10) {
				if(player.y >= (Game.HEIGHT * 64) / 100 && player.y <= (Game.HEIGHT * 64) + 30) {
					if(ui.nDice > 0) {
						storeController.generateItem();
						ui.nDice --;
						ui.itemDetails = null;
					}
				}
			}
		}
	}
	private void deletePokemon() {
		if(pokeList.size() != 1) {
		if ((player.x >= WIDTH * 91 / 100 && player.x <= WIDTH * 91 / 100 + 13) && (player.y >= HEIGHT * 65 / 100 && player.y <= HEIGHT * 65 / 100 + 12)) {
			for (int i = 0; i < pokeList.size(); i++) {
				if (pokeList.get(i).equals(ui.pokemonPcDetails)) {
					pokeList.remove(i);
				}
			}
			for (int i = 0; i < entities.size(); i++) {
				if (entities.get(i).equals(ui.pokemonPcDetails)) {
					entities.remove(i);
				}
			}
			for (int i = 0; i < slotList.size(); i++) {
				if (slotList.get(i).pokemon != null && slotList.get(i).pokemon.equals(ui.pokemonPcDetails)) {
					slotList.get(i).pokemon = null;
					slotList.get(i).slotStatus = "free";
				}
			}
			for (int i = 0; i <slotPcList.size(); i++) {
				if (slotPcList.get(i).pokemon != null && slotPcList.get(i).pokemon.equals(ui.pokemonPcDetails)) {
					slotPcList.get(i).pokemon = null;
					slotPcList.get(i).slotPrincipal = false;
				}
			}

			ui.pokemonPcDetails = null;
	}
	}
	}
	private void pcPageNavegation() {
		if((player.x >= WIDTH * 2/100 && player.x < WIDTH * 2/100 + 20) && (player.y >= HEIGHT* 14/100 && player.y < HEIGHT* 14/100 + 26)) {
			if(ui.pcPage > 1) {
				ui.pcPage--;
				slotPcList.clear();
				for(int i2 = 0; i2 < 3; i2++) {
					for(int i3 = 0; i3 < 6; i3++) {
						SlotPc sp = new SlotPc((Game.WIDTH*6/100 * Game.SCALE )/2 + i3*(Game.WIDTH*12/100)*Game.SCALE,(Game.HEIGHT*44/100 * Game.SCALE)/2 + i2 * (Game.HEIGHT*18/100)*Game.SCALE, (Game.WIDTH*10/100)*Game.SCALE,70, null, null);				
						Game.slotPcList.add(sp);
					}
				}
				for(int i = 0; i < 18; i++) {
					Game.slotPcList.get(i).pokemon = pokeList.get(i+(18*(ui.pcPage-1)));
					if(i+1 > pokeList.size()) {
						break;
					}
				}
			}
		}
		if((player.x >= Game.WIDTH * 64/100 && player.x < Game.WIDTH * 64/100 + 20) && (player.y >= Game.HEIGHT* 14/100 && player.y < Game.HEIGHT* 14/100 + 26)) {			
			if(pokeList.size()+1 > ui.pcPage * 18) {
				ui.pcPage++;
				slotPcList.clear();
				
				for(int i2 = 0; i2 < 3; i2++) {
					for(int i3 = 0; i3 < 6; i3++) {
						SlotPc sp = new SlotPc((Game.WIDTH*6/100 * Game.SCALE )/2 + i3*(Game.WIDTH*12/100)*Game.SCALE,(Game.HEIGHT*44/100 * Game.SCALE)/2 + i2 * (Game.HEIGHT*18/100)*Game.SCALE, (Game.WIDTH*10/100)*Game.SCALE,70, null, null);				
						Game.slotPcList.add(sp);
					}
				}
				for(int i = 0; i < 18; i++) {
					Game.slotPcList.get(i).pokemon = pokeList.get(i+(18*(ui.pcPage-1)));
					if(i+1 > pokeList.size()) {
						break;
					}
				}
			}
		}
	}
	private void draggedInMap() {
		if(player.pokemonDragged != null) {
			for(int i = 0; i < Game.entities.size(); i++) {
				if(Game.entities.get(i) instanceof Locale){
					boolean changePokemons = false;
					boolean changeFirst = false;
					Locale l = (Locale) Game.entities.get(i);
					Locale tempL = null;
					if(l.pokemon != null) {
						changePokemons = true;
					}
					if(Entity.isColiddingPlayer(player, l)) {
						String type[] = pokedex.getInfo(player.pokemonDragged.id, "type").split("\"");
						
						int nType = 0;
						
						if(type.length > 3) {
							nType = 2;	
						}else {
							nType = 1;	
						}
						
						String typeBuff[] = new String[nType];
						boolean isTyped = false;
						boolean isTyped2 = false;
						
						if(nType > 1) {
							typeBuff[0] = type[1]; 
							typeBuff[1] = type[3];
							if(l.type.equalsIgnoreCase(typeBuff[0]) || l.type.equalsIgnoreCase(typeBuff[1])) {
								isTyped = true;
							}
						}else {
							typeBuff[0] = type[1]; 
							if(l.type.equalsIgnoreCase(typeBuff[0])) {
								isTyped = true;
							}
						}					
						if(l.type.equalsIgnoreCase("null") || isTyped) {
							for(int j = 0; j < entities.size();j ++) {
								if(entities.get(j) instanceof Locale) {
									tempL = (Locale) entities.get(j);
									if(tempL.pokemon == player.pokemonDragged) {
										String type2[] = pokedex.getInfo(player.pokemonDragged.id, "type").split("\"");
										
										int nType2 = 0;
										
										if(type2.length > 3) {
											nType2 = 2;	
										}else {
											nType2 = 1;	
										}
										
										String typeBuff2[] = new String[nType];
										
										if(nType2 > 1) {
											typeBuff2[0] = type2[1]; 
											typeBuff2[1] = type2[3];
											if(l.type.equalsIgnoreCase(typeBuff2[0]) || l.type.equalsIgnoreCase(typeBuff2[1])) {
												isTyped2 = true;
											}
										}else {
											typeBuff2[0] = type2[1]; 
											if(l.type.equalsIgnoreCase(typeBuff2[0])) {
												isTyped2 = true;
											}
										}	
										if(changePokemons) {											
											if(tempL.type.equalsIgnoreCase("null") || isTyped) {
												tempL.pokemon = l.pokemon;
												tempL.pokemon.x = player.pokemonDragged.x;
												tempL.pokemon.y = player.pokemonDragged.y;
												tempL.pokemon.initialX = player.pokemonDragged.initialX;
												tempL.pokemon.initialY = player.pokemonDragged.initialY;
												if(isTyped2) {
													tempL.pokemon.aplicateBuffLocale(true);
												}
												changeFirst = true;
											}
										}else {
											
											if(tempL.pokemon.buffed) {
												tempL.pokemon.aplicateBuffLocale(false);	
											}
											tempL.pokemon = null;
										}
										break;
									}
								}
							}
							if(l.type.equalsIgnoreCase("null") || isTyped2){
								if(tempL.pokemon == null) {
									l.setPokemon(player.pokemonDragged);
									player.pokemonDragged.setX(l.x + 7);
									player.pokemonDragged.setY(l.y);
									player.pokemonDragged.initialX = player.pokemonDragged.getX();
									player.pokemonDragged.initialY = player.pokemonDragged.getY();
									
									if(player.pokemonDragged.buffed) {
										player.pokemonDragged.aplicateBuffLocale(false);
										player.pokemonDragged.statsCalculator();
									}
									if(isTyped){					
										player.pokemonDragged.aplicateBuffLocale(true);
									}
								}else {
									if(changeFirst) {
										l.setPokemon(player.pokemonDragged);
										player.pokemonDragged.setX(l.x + 7);
										player.pokemonDragged.setY(l.y);
										player.pokemonDragged.initialX = player.pokemonDragged.getX();
										player.pokemonDragged.initialY = player.pokemonDragged.getY();
										
										if(player.pokemonDragged.buffed) {
											player.pokemonDragged.aplicateBuffLocale(false);
										}
										if(isTyped){					
											player.pokemonDragged.aplicateBuffLocale(true);
										}
									}
								}
							}	
						}
					}
				}
			}
		}
	}
	private void putInMap() {
		for(int i = 0; i < 6; i++) {
			//CHECA QUAL SLOT É SELECIONADO, E SE ELE ESTÁ NO STATUS LIVRE, PARA QUE NÃO POSSA GERAR O MSM POKEMON VARIAS VEZES.
			if(slotList.get(i).isSelected && slotList.get(i).getSlotStatus().equalsIgnoreCase("free") && slotList.get(i).getPokemon() != null) {
				for(int i3 = 0; i3 < Game.entities.size(); i3++) {				
					if(Game.entities.get(i3) instanceof Locale){
						
						Locale l = (Locale) Game.entities.get(i3);
						
						if(Entity.isColiddingPlayer(player, l)) {	
							if(l.pokemon == null) {
								Pokemon pokemon = slotList.get(i).getPokemon();
								String type[] = pokedex.getInfo(pokemon.id, "type").split("\"");
								
								int nType = 0;
								
								if(type.length > 3) {
									nType = 2;	
								}else {
									nType = 1;	
								}
								
								String typeBuff[] = new String[nType];
								boolean isTyped = false;
								
								if(nType > 1) {
									typeBuff[0] = type[1]; 
									typeBuff[1] = type[3];
									if(l.type.equalsIgnoreCase(typeBuff[0]) || l.type.equalsIgnoreCase(typeBuff[1])) {
										isTyped = true;
									}
								}else {
									typeBuff[0] = type[1]; 
									if(l.type.equalsIgnoreCase(typeBuff[0])) {
										isTyped = true;
									}
								}
								if(l.type.equalsIgnoreCase("null") || isTyped) {
									slotList.get(i).setSlotStatus("inMap");
									l.setPokemon(pokemon);
									pokemon.setX(l.x + 7);
									pokemon.setY(l.y);
									pokemon.initialX = pokemon.getX();
									pokemon.initialY = pokemon.getY();
									pokemon.inMap = true;
									if(isTyped){
										pokemon.aplicateBuffLocale(true);
									}									
									entities.add(pokemon);
									slotList.get(i).isSelected = false;
								}
							}
						}					
					}
				}
			}
		}		
	}
	private void slotItemNavegation(){
		if(player.x >= (int)(Game.WIDTH * 84.8 / 100) && player.x <= (int)(Game.WIDTH * 84.8 / 100) + 4) {
			if(player.y >= (Game.HEIGHT * 86.5 / 100) && player.y <= (Game.HEIGHT * 86.5 / 100) + 7) {
				if(ui.nColumn > 0) {
					ui.nColumn--;
				}
			}
		}if(player.x >= (int)(Game.WIDTH * 97.5 / 100) && player.x <= (int)(Game.WIDTH * 97.5 / 100) + 4) {
			if(player.y >= (Game.HEIGHT * 86.5 / 100) && player.y <= (Game.HEIGHT * 86.5 / 100) + 7) {
				if(ui.nColumn < player.listSize -1) {
					ui.nColumn++;
				}
				
			}
		}if(player.x >= (int)(Game.WIDTH * 90.5 / 100) && player.x <= (int)(Game.WIDTH * 90.5 / 100) + 7) {
			if(player.y >= (Game.HEIGHT * 79.6 / 100) && player.y <= (Game.HEIGHT * 79.6 / 100) + 4) {
				ui.nColumn = 0;
				ui.nRow++;				
			}
		}
		if(player.x >= (int)(Game.WIDTH * 90.5 / 100) && player.x <= (int)(Game.WIDTH * 90.5 / 100) + 7) {
			if(player.y >= (Game.HEIGHT * 96 / 100) && player.y <= (Game.HEIGHT * 96 / 100) + 4) {
				if(ui.nRow > 0) {
					ui.nColumn = 0;
					ui.nRow--;
				}
			}
		}
	}
	private void pcSlotClick() {
		for(int i = 0; i < slotPcList.size(); i++) {
			if(Entity.isColidding2(player, slotPcList.get(i))) {
				ui.pokemonPcDetails = slotPcList.get(i).pokemon;
			}
		}
	}
	private void storeSlotClick() {
		for(int i = 0; i < storeController.slotStoreList.size(); i++) {
			if(Entity.isColidding2(player, storeController.slotStoreList.get(i))) {
				ui.itemDetails = storeController.slotStoreList.get(i).item;
			}
		}
	}
	private void mouseReleaseInPokemon() {
		if(player.spriteDesc.equalsIgnoreCase("pokeball")) {
			catchPokemon();
		}else if(player.spriteDesc.equalsIgnoreCase("medicine")) {
			medicineInPokemon();
		}else if(player.spriteDesc.equalsIgnoreCase("battleItem")) {
			battleItemInPokemon();
		}else if(player.spriteDesc.equalsIgnoreCase("evolueItem")) {
			evoltionItemInPokemon();
		}
		
	}
	private void medicineInPokemon() {
		for(int i = 0; i < Game.entities.size(); i++) {				
			if(Game.entities.get(i) instanceof Pokemon){
				Pokemon p = (Pokemon) Game.entities.get(i);
				if(Entity.isColiddingPokeball(player, p)) {
					if(!p.isWild) {
						if(p.currentHp < p.maxHp) {
							Player.medicineItemList.get(ui.nColumn).current --;
							p.currentHp += Player.medicineItemList.get(ui.nColumn).cure;
							if(p.currentHp > p.maxHp) {
								p.currentHp = p.maxHp;
							}
							p.updateLife();
						}
					}
				}
			}
		}
	}
	private void battleItemInPokemon() {
		for(int i = 0; i < Game.entities.size(); i++) {				
			if(Game.entities.get(i) instanceof Pokemon){
				Pokemon p = (Pokemon) Game.entities.get(i);
				if(Entity.isColiddingPokeball(player, p)) {
					if(!p.isWild) {
						if(!p.isHoldding) {
							Player.battleItemList.get(ui.nColumn).current --;
							p.holddingItem = Player.battleItemList.get(ui.nColumn).idItem+1;
							p.statsCalculator();
						}

					}
				}
			}
		}
	}
	private void evoltionItemInPokemon(){
		for(int i = 0; i < Game.entities.size(); i++) {				
			if(Game.entities.get(i) instanceof Pokemon){
				Pokemon p = (Pokemon) Game.entities.get(i);
				if(Entity.isColiddingPokeball(player, p)) {
					if(!p.isWild) {
						if(p.itemToEvolue.equalsIgnoreCase(Player.evolueItemList.get(ui.nColumn).name)) {
							Player.evolueItemList.get(ui.nColumn).current --;
							p.evolue();
						}
					}
				}
			}
		}
	}
	public void catchPokemon() {
		player.sprite = null;
		player.spriteDesc = ""; 
		
		for(int i = 0; i < Game.entities.size(); i++) {				
			if(Game.entities.get(i) instanceof Pokemon){
				Pokemon p = (Pokemon) Game.entities.get(i);
				if(Entity.isColiddingPokeball(player, p)) {
					if(Player.pokeballItemList.get(ui.nColumn).name.equalsIgnoreCase("Masterball") && p.isBoss == false && p.withOwner) {
						captureChanges(p);
					}else if((p.isWild && p.isBoss == false && p.withOwner == false)) {
						
						int n = random.nextInt(100);
						int probPokemon = Math.round((Integer.parseInt(pokedex.getInfo(p.id, "catchRate")) * 100) / 255);
						double lifePercent = p.currentHp * 100 / p.maxHp;
						Player.pokeballItemList.get(ui.nColumn).current --;
						int p1 = 3;
						int p2 = 3;
						int p3 = 1;
						int probFinal = (int)((probPokemon) * p1 + (Player.pokeballItemList.get(ui.nColumn).catchRate * p2) + (100 - lifePercent) * p3)/(p1 + p2+ p3);
						if(n+1 <= probFinal) {								
							captureChanges(p);
							
						}
						
					}
					
				}
			}
							
		}
		
	}
	private void captureChanges(Pokemon p) {
		p.domesticPokemon();
		for(int i2 = 0; i2 < 6; i2++){
			if(slotList.get(i2).getPokemon() == null) {
				slotList.get(i2).setPokemon(p);
				break;
			}
		}
		for(int i3 = 0; i3 < p.attakers.size(); i3++){	
			p.attakers.get(i3).target = null;									
		}
        
        
        for(int j = 0; j < Game.slotPcList.size(); j++) {
        	if(slotPcList.get(j).pokemon == null) {
        		slotPcList.get(j).pokemon = p;
        		slotPcList.get(j).sprite = pokedex.getSprite(p.id, "front", p.isShiny);
        		break;
        	}
        	
        }
        
		p.defeatEnemy(p);
		waveList.remove(p);
		entities.remove(p);
		Game.lvlConfig.get(Game.currentLvl).myVictorys++;
		lvlConfig.get(currentLvl).totalDefeat++;
	}

}
