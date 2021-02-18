import java.io.Serializable;
import java.util.List;
import java.lang.Iterable;

public interface PaperCollection extends Serializable, Iterable<String> {
  public String getName();

  public int getRevision();

  public List<String> getList();

  public String getItem(int index);

  public double averagePaperLength();
}
