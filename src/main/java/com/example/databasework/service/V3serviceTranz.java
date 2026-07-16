package com.example.databasework.service;

import com.example.databasework.dto.DtoTranzaktion;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class V3serviceTranz {

    private final DataSource dataSource;

    public V3serviceTranz(@Qualifier("postgresDataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }
//        public V3serviceTranz(DataSource dataSource) {
//            this.dataSource = dataSource;
//        }

    public void transfer(DtoTranzaktion request) {
        Connection connection = null;


        //todo to user try with resource
        try {
            connection = dataSource.getConnection();
            System.out.println("Подключение к БД удалось...");

            connection.setAutoCommit(false);
            // Получаем баланс отправителя
            PreparedStatement select = connection.prepareStatement(
                    "SELECT balance FROM account WHERE id = ?"
            );

            select.setInt(1, request.getFrom());
            ResultSet result = select.executeQuery();

            if (result.next()) {

                BigDecimal balance = result.getBigDecimal("balance");

                System.out.println(balance);

                if (balance.compareTo(request.getAmount()) < 0) {
                    throw new RuntimeException("Недостаточно средств");
                }

                if (balance.compareTo(request.getAmount()) >= 0) {

                    // Списание деньг
                    PreparedStatement tranz = connection.prepareStatement(
                            "UPDATE account SET balance = balance - ? WHERE id = ?"
                    );

                    tranz.setBigDecimal(1, request.getAmount());
                    tranz.setInt(2, request.getFrom());

                    tranz.executeUpdate();

                    // Пополнение счета
                    PreparedStatement deposit = connection.prepareStatement(
                            "UPDATE account SET balance = balance + ? WHERE id = ?"
                    );

                    deposit.setBigDecimal(1, request.getAmount());
                    deposit.setInt(2, request.getTo());

                    deposit.executeUpdate();

                    connection.commit();

                    System.out.println("Перевод выполнен");
                }

            } else {

                throw new RuntimeException("Счет отправителя не найден");

            }

        } catch (Exception e) {

            try {

                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();

        } finally {

            try {

                if (connection != null) {
                    connection.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public String getInfo() {

        //try with resources

        //разница межд
        try (Connection connection = dataSource.getConnection()) {

            String sql = "SELECT id, owner, balance FROM account";

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();

            StringBuilder info = new StringBuilder();

            while (rs.next()) {

                info.append("ID: ")
                        .append(rs.getInt("id"))
                        .append(", OWNER: ")
                        .append(rs.getString("owner"))
                        .append(", BALANCE: ")
                        .append(rs.getBigDecimal("balance"))
                        .append("\n");
            }

            return info.toString();

        } catch (SQLException e) {

            System.out.println("Ошибка: " + e.getMessage());

            return "Ошибка получения данных";
        }
    }
}


