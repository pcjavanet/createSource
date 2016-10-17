import java.io.File;

public class CreateJsPath {

	public static void print( File f){
		if ( f.isDirectory()) {
			File[]  fs =  f.listFiles() ;
			for( int i=0 ; i<fs.length;i++){
				print(fs[i]);
			}
		}else {
			System.out.println(f.getName());
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String baseDir = "/home/pcjavanet/devTool/cdcWorkspace/IMES/imesWeb/src/main/webapp/static/app";
		File f  = new File( baseDir);
		 print(f);
	}

}
