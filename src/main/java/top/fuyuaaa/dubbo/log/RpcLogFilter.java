package top.fuyuaaa.dubbo.log;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.rpc.*;

/**
 * @author : fuyuaaa
 * @date : 2020-10-28 15:02
 */
@Slf4j
public class RpcLogFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        long startTime = System.currentTimeMillis();
        Result result = invoker.invoke(invocation);
        long endTime = System.currentTimeMillis();
        log.info("[rpc record], method:{}, spend:{}ms, timeOut:{}, params:{}",
                invoker.getInterface().getSimpleName() + "#" + invocation.getMethodName(),
                endTime - startTime,
                endTime - startTime >= 1000L,
                JSON.toJSONString(invocation.getArguments()));
        if (result.hasException()) {
            log.error("[rpc error], ", result.getException());
        }
        return result;
    }
}
