package kaleb.world;

public class Camera {
	
	public static int x = 0;
	public static int y = 0;
	
	public static double clamp(double Atual, double Min, double Max) {
		if(Atual < Min) {
			Atual = Min;
		}
		
		if(Atual > Max) {
			Atual = Max;
		}
		
		return Atual;
	}
	public static int clamp2(int Atual, int Min, int Max) {
		if(Atual < Min) {
			Atual = Min;
		}
		
		if(Atual > Max) {
			Atual = Max;
		}
		
		return Atual;
	}
	
}
