package shb.slc.amqp;

public enum RabbitmqExchangeType {
    DIRECT("direct"),
    TOPIC("topic"),
    FANOUT("fanout"),
    HEADER("headers");

    private final String exchangeName;

    RabbitmqExchangeType(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public String getExchangeName() {
        return this.exchangeName;
    }
}
