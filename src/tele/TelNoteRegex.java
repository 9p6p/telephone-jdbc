package tele;

import java.util.Scanner;

/**
 * 数据校验类
 */
public class TelNoteRegex {
    /**
     * 对菜单输入选项的验证
     *
     * @param min 最小值
     * @param max 最大值
     * @return int
     */
    public int menuItemValidate(int min, int max) {
        // 定义验证菜单项的正则表达式
        String regex = "[1-9]";
        // 创建键盘输入对象
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("请输入正确数字，最小是：" + min + "\t" + "最大是：" + max);
            String input = scanner.nextLine();
            if (input.matches(regex)) {
                int inputNum = Integer.parseInt(input);
                if (inputNum >= min && inputNum <= max) {
                    return inputNum;
                } else {
                    System.out.println("您输入的菜单项不符，请重新输入！");
                }
            } else {
                System.out.println("输入数字错误，请检查！");
            }
        }
    }

    /**
     * 对用户输入姓名的验证
     * 字母可以是大写或者小写，长度1-10之间的
     *
     * @return String
     */
    public String nameValidate() {
        // 对姓名校验的正则表达式
        String regex = "[a-zA-Z]{1,10}";
        // 创建Scanner对象
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("请输入姓名，格式为长度1-10之间的大写或小写字母");
            String input = scanner.nextLine();
            if (input.matches(regex)) {
                return input;
            } else {
                System.out.println("您输入的姓名有误，请重新输入！");
            }
        }
    }

    /**
     * 对用户输入年龄的验证
     * 年龄的格式要求：10-99之间的
     *
     * @return String
     */
    public String ageValidate() {
        // 对年龄校验的正则表达式
        String regex = "[1-9][0-9]";
        // 创建Scanner对象
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("请输入年龄，格式为10-99之间：");
            String input = scanner.nextLine();
            if (input.matches(regex)) {
                return input;
            } else {
                System.out.println("您输入的年龄有误，请重新输入！");
            }
        }
    }

    /**
     * 对用户输入性别的验证
     * 性别的输入要求：男 or 女
     *
     * @return String
     */
    public String sexValidate() {
        // 对性别校验的正则表达式
        String regex = "[男|女]";
        // 创建Scanner对象
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("请输入性别：");
            String input = scanner.nextLine();
            if (input.matches(regex)) {
                return input;
            } else {
                System.out.println("您输入的性别有误，请重新输入！");
            }
        }
    }

    /**
     * 对用户输入电话号码的验证
     * 电话号码要求：允许带有区号的座机号，允许手机号
     *
     * @return String
     */
    public String telNumValidate() {
        // 对电话号码校验的正则表达式
        String regex = "(\\d{3,4}-\\d{7,8})|([1]\\d{10})";
        // 创建Scanner对象
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("请输入电话号码，可以是座机号或者是手机号：");
            String input = scanner.nextLine();
            if (input.matches(regex)) {
                return input;
            } else {
                System.out.println("您输入的电话号码有误，请重新输入！");
            }
        }
    }

    /**
     * 对用户输入地址的验证
     * 地址格式要求：由字母或者数字，长度1-50
     *
     * @return String
     */
    public String addressValidate() {
        // 对地址校验的正则表达式
        String regex = "\\w{1,50}"; //\\W 汉字
        // 创建Scanner对象
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("请输入地址，格式为字母或数字，长度为1-50：");
            String input = scanner.nextLine();
            if (input.matches(regex)) {
                return input;
            } else {
                System.out.println("您输入的地址有误，请重新输入！");
            }
        }
    }

}
