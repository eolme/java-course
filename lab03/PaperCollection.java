import java.util.List;

public interface PaperCollection {
  public String getName();

  public int getRevision();

  public List<String> getList();

  public double averagePaperLength();
}
