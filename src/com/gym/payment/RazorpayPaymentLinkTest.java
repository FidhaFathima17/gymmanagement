package com.gym.payment;

import com.razorpay.PaymentLink;

public class RazorpayPaymentLinkTest {

    public static void main(String[] args) {

        try {

            RazorpayService service =
                    new RazorpayService();

            PaymentLink paymentLink =
                    service.createPaymentLink(
                            10.00,
                            "Test Gym Membership",
                            "Test Customer",
                            "test@example.com",
                            "9045678912"
                    );

            System.out.println(
                    "================================="
            );

            System.out.println(
                    "RAZORPAY PAYMENT LINK CREATED"
            );

            System.out.println(
                    "================================="
            );

            System.out.println(
                    "Payment Link ID: "
                            + service.getPaymentLinkId(paymentLink)
            );

            System.out.println(
                    "Payment URL: "
                            + service.getPaymentUrl(paymentLink)
            );

            System.out.println(
                    "Status: "
                            + paymentLink.get("status")
            );

            System.out.println(
                    "================================="
            );

        } catch (Exception e) {

            System.out.println(
                    "================================="
            );

            System.out.println(
                    "RAZORPAY PAYMENT LINK ERROR"
            );

            System.out.println(
                    "================================="
            );

            e.printStackTrace();
        }
    }
}