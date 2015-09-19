public class Runner {
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("===========================");
		System.out.println("=========WELCOME===========");
		System.out.println("===========================");
		System.out.println("Type (s) start: (q) to quit");
		System.out.println("===========================");
		
	Scanner s = new Scanner(System.in);
	
	while(!s.next().equals("q"))
		{
				
			switch (s.next())
			{
			case "s":
				ImaSnake snake = new ImaSnake();
				GUI g = new GUI();
				break;
				
				default:
			}
		}
	}

}
