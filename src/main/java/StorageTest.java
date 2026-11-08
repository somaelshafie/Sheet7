import java.io.IOException;

public class StorageTest {
    public static void main(String[] args){
        StorageDevice s=new FlashDrive();
        String [] arr=new String[6];
        arr[0]=null;
        arr[1]=new String("oldfile.old");
        arr[2]=new String("doc_denied.txt");
        arr[3]=new String("infected_virus.data");
        arr[4]=new String("temp.tmp");
        arr[5]=new String("normal.txt");
        for(int i=0;i<arr.length;i++){
            try {
               System.out.println(s.readData(arr[i]));
            }
            catch (CorruptedFileException e){
                System.out.println( e.getMessage());
            }
            catch (StorageException e){
                System.out.println( e.getMessage());
            }
            catch (IOException e){
                System.out.println( e.getMessage());
            }
            catch (NullPointerException e){
                System.out.println( e.getMessage());
            }
        }

    }
}

class StorageException extends Exception{
    public StorageException(String message){
        super(message);
    }
}
class CorruptedFileException extends StorageException{
    public CorruptedFileException(String message){
        super(message);
    }
}
class StorageDevice{
    String readData(String fileName)throws StorageException,IOException{

            if (fileName == null) {
                throw new NullPointerException("fileName must not be null");
            }

            if (fileName.endsWith(".old")) {
                throw new StorageException("unsupported format");
            }
            if (fileName.contains("denied")) {
                throw new IOException("access denied");
            }
             return "data from storage device: "+fileName;



    }
}
class FlashDrive extends StorageDevice{
    @Override
    String readData(String fileName)throws StorageException,IOException{
        if(fileName==null){
            throw new NullPointerException("fileName must not be null");
        }
        if(fileName.contains("virus")){
            throw new CorruptedFileException("unsupported formate");
        }
        if(fileName.endsWith(".tmp")){
            throw new IOException("temporary file cannot be read");
        }
        return "data from flash drive: "+fileName;
    }
}

