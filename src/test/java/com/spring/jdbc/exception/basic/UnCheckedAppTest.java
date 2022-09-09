package com.spring.jdbc.exception.basic;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;
import java.sql.SQLException;

public class UnCheckedAppTest {

    @Test
    void unchecked() {
        Controller controller = new Controller();
        Assertions.assertThatThrownBy(() -> controller.request())
                .isInstanceOf(RuntimeException.class);
    }

    static class Controller {
        Service service = new Service();

        public void request() {
            service.logic();
        }
    }

    static class Service {

        Repository repository = new Repository();
        NetWorkClient netWorkClient = new NetWorkClient();

        public void logic() {
            repository.call();
            netWorkClient.call();
        }

    }

    static class NetWorkClient {
        public void call() {
            throw new RUntimeConnectException("연결 실패");
        }
    }

    static class Repository {
        public void call() {
            try {
                runQSL();
            } catch (SQLException e) {
                throw new RuntimeSQLException(e);
            }
        }

        public void runQSL () throws SQLException {
            throw new SQLException("ex");
        }
    }

    static class RUntimeConnectException extends RuntimeException {
        public RUntimeConnectException(String message) {
            super(message);
        }
    }

    static class RuntimeSQLException extends RuntimeException {
        public RuntimeSQLException(Throwable cause) {
            super(cause);
        }
    }
}
