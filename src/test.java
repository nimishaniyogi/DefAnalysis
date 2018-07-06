
public class test {
	public static void main (String [] args) {
        test rdt1 = new test();
        rdt1.m(8);
    }

    public void m(int i){
        int a = 4;
        int y = 3;
        if (i < 10) {
            a = 5;
        }
        else if (i == 10){
            a = 6;
        }
        else {
            a = 7;
        }
        int j = a * y;
        
    }
    
    public void n(int i){
        int a = 4;
        if (i < 10){
            a = 5;
        }
        else if (a == 10){
            a = 6;
        }
        
        System.out.println(a);
    }
}
