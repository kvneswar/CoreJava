import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.customannotations.FileInfo;
import com.customannotations.RecordInfo;
import com.model.Tokenize;


public class Main {

	public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		
		String str = "token1|token2|token3";
		Tokenize tokenize = Tokenize.class.newInstance();
		
		Class tokenizeClass = Tokenize.class;
		if(tokenizeClass.isAnnotationPresent(FileInfo.class)){
			Annotation fileAnnotation = tokenizeClass.getAnnotation(FileInfo.class);
			FileInfo fileInfo = (FileInfo)fileAnnotation;
			String[] tokens = str.split(fileInfo.delimiter());
			if(tokens != null && tokens.length >0 ){
				for(Method method:tokenizeClass.getDeclaredMethods()){
					if(method.isAnnotationPresent(RecordInfo.class)){
						Annotation methodAnnotation = method.getAnnotation(RecordInfo.class);
						RecordInfo recordInfo = (RecordInfo)methodAnnotation;
						method.invoke(tokenize, tokens[recordInfo.position()-1]);	
					}
					
				}
			}
		}
		System.out.println(tokenize);
	}

}
