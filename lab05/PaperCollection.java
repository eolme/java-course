import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.util.List;

public interface PaperCollection extends Serializable {
  public String getName();

  public int getRevision();

  public List<String> getList();

  public double averagePaperLength();

  public void input(InputStream in);

  public void output(OutputStream out);

  public void read(Reader in);

  public void write(Writer out);
}
