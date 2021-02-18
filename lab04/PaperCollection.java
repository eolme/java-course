import java.io.Serializable;
import java.util.List;

public interface PaperCollection extends Serializable {
  public String getName();

  public int getRevision();

  public List<String> getList();

  public String getItem(int index);

  public double averagePaperLength();
}
