package example.logging.logback.enhancers

import com.google.cloud.logging.LogEntry;
import com.google.cloud.logging.LoggingEnhancer;


class ExampleEnhancer : LoggingEnhancer {


    override fun enhanceLogEntry(logEntry: LogEntry.Builder) {
        // add additional labels
        logEntry.addLabel("test-label-1", "test-value-1")
    }
}
