import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class AnnotationTest {
	public static void main(String[] args) {
		//wait wait wait notify notifyAll
		//hashCode,equals, toString, finalize, clone
		//getClass
		
		PickleJar pickleJar = new PickleJar();
		
		
		//every object of an "Object class"
		//got a method called as getClass()
		//that returns the object of class Class
		
		Class theMirror = pickleJar.getClass();
		
		Annotation annos [] = theMirror.getAnnotations();	
		
		for (Annotation annotation : annos) {
			if(annotation instanceof DevelopedBy) {
				DevelopedBy devBy = (DevelopedBy) annotation;
				if(devBy.name().equals("Mitali")) {
					System.out.println("YES, developed by Mitali...");
					if(devBy.version() == 2.0 ) {
						System.out.println("YES, version is 2.0");
						
						pickleJar.openJar();
						pickleJar.refillJar();
						
						Method methods[] = theMirror.getMethods();
						
						for(Method method : methods) {
							
							//System.out.println("METHOD : "+method.getName());
							Annotation methodAnnos[]= method.getAnnotations();
							for (Annotation methodAnno : methodAnnos) {
								//System.out.println("Annotation on "+method.getName()+ " is "+methodAnno);
								if(methodAnno instanceof DontForget) {
									DontForget dontForget = (DontForget) methodAnno;
									
									if(dontForget.message().equals("closed tightly")) {
										pickleJar.closeJar();
									}
									else {
										System.out.println("Jar is not closed tightly....its left open...");
									}
								}
							}
						}
				
//						
//						
//						
					}
					else {
						System.out.println("NO, version is not 2.0...");
					}			
				}
				else {
					System.out.println("Not developed by Mitali...discarding the operation....");
				}
			}
		}
		
		
	}
	
}

@DevelopedBy(name="Mitali", version=2.0)
class PickleJar
{
	
	private String content;
	public void openJar() {	System.out.println("opening the jar...");	}
	
	@DontForget(message="closed tightly")
	public void closeJar() {  System.out.println("closing the jar....");}
	public void refillJar() { System.out.println("refilling the jar..");}
}
