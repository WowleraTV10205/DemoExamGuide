package demoexam.ui;

import demoexam.utils.BaseForm;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;

public class ClientTableForm extends BaseForm {
    private JPanel mainPanel;
    private JTable table;


    public ClientTableForm() {
        //выбор панели
        setVisible(true);
        pack();
        setContentPane(mainPanel);
        //увеличение строк
        table.setRowHeight(25);
        // Создание модели данных для таблицы
        DefaultTableModel client_mdl = new DefaultTableModel();
        // Указание названий столбцов
        client_mdl.setColumnIdentifiers(new String[]{"ID","Имя","Фамилия","Отчество","Дата рождения","Дата регистрации","Почта","Номер телефона","Пол","Фото"});

        // Ловятся исключения, связанные с БД
        try {
            // Подключение к БД new_schema по адресу localhost, пользователь - root, пароль - 123456
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/demoexam?serverTimeZone=UTC","root","");


            // Подготовка запроса
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM client");
            // Выолнение запроса, в ResultSet помещаются строки из БД
            ResultSet resultSet = preparedStatement.executeQuery();

            // Прогон по каждой cтроке БД по порядку, пока строки существуют
            while (resultSet.next()){
                // Добавление в модель строки с данными из БД
                client_mdl.addRow(new String[]{
                        // Взятие из строки ResultSet'а значения из первого столбца
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getString(9).equals("м") ? "Мужской" : "Женский",
                        resultSet.getString(10)
                });
            }

            connection.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        // Установка созданной модели на объект таблицы
        table.setModel(client_mdl);

        table.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int row = table.getSelectedRow();
                if (row != -1 && e.getKeyCode() == KeyEvent.VK_DELETE) {
                    if (JOptionPane.showConfirmDialog(null, "Вы хотите удалить данную строку?", "Внимание", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        int id = Integer.parseInt((String) table.getValueAt(row, 0));
                        try {
                            Connection c = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/demoexam?serverTimeZone=UTC", "root", "");
                            String sql = "DELETE FROM client WHERE id=?";
                            PreparedStatement ps = c.prepareStatement(sql);
                            ps.setInt(1, id);
                            ps.executeUpdate();
                            new ClientTableForm();
                            dispose();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}