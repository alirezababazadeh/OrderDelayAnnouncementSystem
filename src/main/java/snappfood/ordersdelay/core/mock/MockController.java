package snappfood.ordersdelay.core.mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class MockController {

    private final MockDataGenerator mockDataGenerator;

    @Autowired
    public MockController(MockDataGenerator mockDataGenerator) {
        this.mockDataGenerator = mockDataGenerator;
    }

    @PostMapping("/mock")
    public ResponseEntity<String> generateMockData() {
        mockDataGenerator.generate();
        return ResponseEntity.status(HttpStatus.CREATED.value()).body("Success");
    }
}
