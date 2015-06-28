import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) {

	List<ParentClass> list = new ArrayList<ParentClass>();   // It works
	
	//List<ParentClass> list = new ArrayList<ChildClass>();  // Won't compile
	
	List<?> list1 = new ArrayList<ChildClass>();		 // It works
	
	List<? extends ParentClass> list2 = new ArrayList<ChildClass>(); // It works
	
	List<? extends ChildClass> list3 = new ArrayList<ChildClass>(); // It works
	
	List<? super ChildClass> list4 = new ArrayList<ChildClass>(); // It works
	
	List<? super ChildClass> lists = new ArrayList<ParentClass>(); // It works

    }

}
