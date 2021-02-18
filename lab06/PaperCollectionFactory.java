import java.util.List;

public interface PaperCollectionFactory {
  public PaperCollection createInstance();

  public PaperCollection createInstance(String name, int revision, List<String> list) throws Exception;
}
