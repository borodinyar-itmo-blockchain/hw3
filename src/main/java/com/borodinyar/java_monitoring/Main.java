package com.borodinyar.java_monitoring;

import com.borodinyar.java_monitoring.generated.Exchange_rate;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.EventValues;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.websocket.WebSocketService;
import org.web3j.protocol.websocket.events.Log;

import java.math.BigInteger;
import java.net.ConnectException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Main {
    public static void main(String[] args) throws ConnectException {
        Config config = new Config();
        WebSocketService webSocketService = new WebSocketService(config.getNodeUrl(), false);
        webSocketService.connect();
        Web3j web3 = Admin.build(webSocketService);


        var sub = web3.blockFlowable(false).subscribe(block -> {
            System.out.printf("New block: ts=%d, number=%d, hash=%s \n",
                    block.getBlock().getTimestamp(),
                    block.getBlock().getNumber(),
                    block.getBlock().getHash());
        });

        config.getOracles().forEach((token, address) -> {
           web3.logsNotifications(
                    List.of(address),
                    List.of(EventEncoder.encode(Exchange_rate.ANSWERUPDATED_EVENT))
            ).subscribe(
                    log -> { parseLog(token, log.getParams().getResult()); }
            );
        });
    }

    private static void parseLog(String token, Log log) {
        List<String> topics = log.getTopics();

        var eventIndexedParameters = Exchange_rate.ANSWERUPDATED_EVENT.getIndexedParameters();

        var indexedValues = IntStream.range(0, eventIndexedParameters.size())
                .mapToObj(i -> FunctionReturnDecoder.decodeIndexedValue(
                        topics.get(i + 1), eventIndexedParameters.get(i)
                )).collect(Collectors.toList());
        EventValues values = new EventValues(
                indexedValues,
                FunctionReturnDecoder.decode(log.getData(), Exchange_rate.ANSWERUPDATED_EVENT.getNonIndexedParameters())
        );

        BigInteger current = (BigInteger) values.getIndexedValues().get(0).getValue();
        BigInteger roundId = (BigInteger) values.getIndexedValues().get(1).getValue();
        BigInteger updatedAt = (BigInteger) values.getNonIndexedValues().get(0).getValue();
        System.out.printf(
                "Contract %s is updated: ts=%d, current=%d, roundId=%d \n",
                token,
                updatedAt,
                current,
                roundId
        );
    }

}

