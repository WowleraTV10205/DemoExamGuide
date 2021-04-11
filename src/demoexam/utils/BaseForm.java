package demoexam.utils;

import javax.swing.*;
import java.awt.*;

public class BaseForm extends JFrame {

    public BaseForm(){
        setTitle("ООО Ебатория");//название приложения
        setMinimumSize(new Dimension(1400,800)); //установка разрешения окна
        setLocationRelativeTo(null); //центрирование окна
        setIconImage(new ImageIcon(getClass().getResource("imgs/school_logo.png")).getImage());//добавление иконки
    }
}