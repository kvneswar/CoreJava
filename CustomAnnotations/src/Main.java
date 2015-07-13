import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.customannotations.FileInfo;
import com.customannotations.RecordInfo;
import com.model.Tokenize;


public class Main {

	public static void main(String[] args) throws Exception {
		
		String str = "to*ken1|token2|token3";
		Tokenize tokenize = Tokenize.class.newInstance();
		
		Class tokenizeClass = Tokenize.class;
		if(tokenizeClass.isAnnotationPresent(FileInfo.class)){
			Annotation fileAnnotation = tokenizeClass.getAnnotation(FileInfo.class);
			FileInfo fileInfo = (FileInfo)fileAnnotation;
			String[] tokens = str.split(fileInfo.delimiter());
			String token = null;
			if(tokens != null && tokens.length >0 ){
				for(Method method:tokenizeClass.getDeclaredMethods()){
					if(method.isAnnotationPresent(RecordInfo.class)){
						Annotation methodAnnotation = method.getAnnotation(RecordInfo.class);
						RecordInfo recordInfo = (RecordInfo)methodAnnotation;
						token = tokens[recordInfo.position()-1];
						if(token.matches(recordInfo.regExPattern())){
							method.invoke(tokenize, token);
						}else{
							throw new Exception("Validation failed for the token: "+token+", Regex"+recordInfo.regExPattern());
						}
							
					}
					
				}
			}
		}
		System.out.println(tokenize);
	}

}
