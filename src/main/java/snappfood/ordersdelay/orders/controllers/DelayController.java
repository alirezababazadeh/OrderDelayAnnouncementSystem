package snappfood.ordersdelay.orders.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import snappfood.ordersdelay.orders.controllers.context.DelayAnnouncementRequest;
import snappfood.ordersdelay.orders.domain.exceptions.DelayAssigmentException;
import snappfood.ordersdelay.orders.domain.exceptions.DelayReportException;
import snappfood.ordersdelay.orders.domain.services.DelayService;

@RestController
@RequestMapping("/api/v1")
public class DelayController {

    private final DelayService delayService;

    @Autowired
    public DelayController(DelayService delayService) {
        this.delayService = delayService;
    }

    @PostMapping(value= "/delay-announcement")
    @ResponseBody
    public ResponseEntity<String> announceDelayReport(@RequestBody DelayAnnouncementRequest request) {
        try {
            return ResponseEntity
                    .status(HttpStatusCode.valueOf(HttpStatus.CREATED.value()))
                    .body(delayService.createOrderReport(request.getOrderId()));
        } catch (DelayReportException exception) {
            return ResponseEntity
                    .status(HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()))
                    .body(exception.getMessage());
        }
    }

    @GetMapping(value = "/delay-assignment/{agent_id}")
    @ResponseBody
    public ResponseEntity<String> assignDelayToAgent(@PathVariable(name = "agent_id") long agentId) {
        try {
            return ResponseEntity
                    .status(HttpStatusCode.valueOf(HttpStatus.OK.value()))
                    .body(delayService.assignReportToAgent(agentId));
        } catch (DelayAssigmentException exception) {
            return ResponseEntity
                    .status(HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()))
                    .body(exception.getMessage());
        }
    }

    @GetMapping(value = "/vendors-delays")
    @ResponseBody
    public ResponseEntity<Object> generateVendorDelaysReport() {
        try {
            return ResponseEntity
                    .status(HttpStatusCode.valueOf(HttpStatus.CREATED.value()))
                    .body(delayService.generateVendorDelayReport());
        } catch (Throwable exception) {
            return ResponseEntity
                    .status(HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()))
                    .body(exception);
        }
    }
}
