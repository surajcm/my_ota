package com.online.travel.graphql;

import com.online.travel.exception.MyOtaException;
import graphql.GraphQLError;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

@Component
public class GraphQlExceptionResolver extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(final Throwable ex, final DataFetchingEnvironment env) {
        if (ex instanceof MyOtaException myOtaException) {
            return GraphQLError.newError()
                    .errorType(toErrorType(myOtaException))
                    .message(myOtaException.getErrorMessage())
                    .path(env.getExecutionStepInfo().getPath())
                    .location(env.getField().getSourceLocation())
                    .build();
        }
        return null;
    }

    private ErrorType toErrorType(final MyOtaException ex) {
        if (ex.getStatus() != null && ex.getStatus().is4xxClientError()) {
            return ErrorType.BAD_REQUEST;
        }
        return ErrorType.INTERNAL_ERROR;
    }
}
