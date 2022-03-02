package tele;

/**
 * 电话本项目入口类
 */
public class App {
    /**
     * 启动电话本项目
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        App app = new App();
        app.start();
    }

    /**
     * 控制主菜单
     */
    public void start() {
        Menu menu = new Menu();
        TelNoteRegex regex = new TelNoteRegex();
        PersonDAO operate = new PersonDAO();
        while (true) {
            menu.mainMenu();
            int item = regex.menuItemValidate(1, 6);
            switch (item) {
                case 1 -> operate.addLogic();
                case 2 -> operate.searchLogic();
                case 3 -> operate.modifyLogic();
                case 4 -> operate.deleteLogic();
                case 5 -> operate.orderLogic();
                case 6 -> System.exit(0);
            }
        }
    }
}
