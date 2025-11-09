import controller.RouteController;
import repository.FakeBussAPI;
import view.TerminalView;

public class main {
}
public static void main(String[] args) {


// Opprett nødvendige objekter
    FakeBussAPI api = new FakeBussAPI();
    TerminalView view = new TerminalView();
    RouteController controller = new RouteController(api, view);

    // Start test-session
    Scanner scanner = new Scanner(System.in);
    controller.velgReise(scanner);

}