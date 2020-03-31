import java.util.Arrays;
import java.util.Random;



public class TreeTester {
	
	public static int getRandomIntegerBetweenRange(double min, double max){
	    int x = (int)Math.floor((Math.random()*((max-min)+1))+min);
	    return x;
	}
	
	public static void main(String[] args) {
		int size=100;
		AVLTree t = new AVLTree();
		double sum_insert=0;
		int max_insert=0;
		int temp;
		double sum_del=0;
		int max_del=0;
		int random_val;
		int [] keys= new int [size];
		for (int i=1;i<size;i++) {
			do {
				random_val=getRandomIntegerBetweenRange(1, 1000000);
			} while (t.search(random_val)!=null);
			temp=t.insert(random_val, Integer.toString(i));
			keys[i]=random_val;
			sum_insert+=temp;
			if (temp>max_insert) {
				max_insert=temp;
			}
		}
		int rnd = new Random().nextInt(keys.length);
		int value=keys[rnd];
		AVLTree [] test = t.split(value);
		test[0].print2DUtil(test[0].getRoot(), 1);
		test[1].print2DUtil(test[1].getRoot(), 1);
		System.out.println(max_insert);
		System.out.println(sum_insert/size);
		System.out.println("-----deleting-----");
		/*
		
		  for (int i=1;i<keys.length;i++) {
		 
			temp=t.delete(keys[i]);
			sum_del+=temp;
			if (temp>max_del) {
				max_del=temp;
			}
		}
		System.out.println(max_del);
		System.out.println(sum_del/size);
		/*AVLTree t = new AVLTree();
		t.insert(5, "a");
		t.insert(2, "a");
		t.insert(8, "a");
		t.insert(7, "a");
		t.print2DUtil(t.getRoot(), 1);
		t.delete(2);*/
		
		
	}
		/*
		/*
		AVLTree t1 = new AVLTree();
		AVLTree t2 = new AVLTree();
		AVLTree t3 = new AVLTree();
		t1.insert(1, "");
		t1.insert(2, "");
		t1.print2DUtil(t1.getRoot(), 2);
		t2.insert(3, "");
		t3.insert(4, "");
		t1.join(t2.getRoot(), t3);
		t1.print2DUtil(t1.getRoot(), 2);
		
		
		
		AVLTree t = new AVLTree();
		int size =10000;
		int random_val;
		int [] keys= new int [size];
		for (int i=1;i<size;i++) {
			do {
				random_val=getRandomIntegerBetweenRange(1, 10000000);
			} while (t.search(random_val)!=null);
			t.insert(random_val, Integer.toString(i));
			keys[i]=random_val;
		}
		
		int thisI;
		int thisD;
		double avgI;
		double avgD;
		int maxI = 0;
		int maxD = 0;
		int countInsert = 0;
		int countDelete = 0;
		
		System.out.println("*****************************************************");
			AVLTree mytree = new AVLTree();
			//countInsert = 0;
			int j=10;
			int [] myArr = new int[j*10000];
		//	insert 
			for (int i = 0; i < j * 10000; i++) {
				int rnd = new Random().nextInt(1000000000);
				//System.out.println(i+"              "+rnd);
				mytree.insert(rnd, "c");
				myArr[i] = rnd;
			}
			
			//split
			int rnd = new Random().nextInt(myArr.length);
			int value=myArr[rnd];
			
			System.out.println("split by     "+value);
			mytree.split(value);
			/*
			
			AVLTree splitter = new AVLTree();
			splitter.root = mytree.getRoot().getLeft();
			int split = splitter.max2().getKey();
			mytree.split(split);
			*/
			/** delete 
			for (int i = 0; i < treeArr.length; i++) {
				thisD=mytree.delete(treeArr[i]);
				countDelete += thisD;
				if (thisD>maxD)
				{
					maxD=thisD;
				}
				}
				*/
			/**part 1
			int div = j * 10000;
			System.out.println("for " + j * 10000);
			System.out.println("balance insert:" + countInsert);
			avgI = (double)countInsert / div;
			System.out.println("avg insert:" + avgI);
			System.out.println("maxInsert: "+maxI);
			System.out.println("balance Delete:" + countDelete);
			avgD = (double)countDelete / div;
			System.out.println("avg Delete:" + avgD);
			System.out.println("maxDelete: "+maxD);
*/
			

		}

		// mytree.print2DUtil(mytree.getRoot(), 5);
	


