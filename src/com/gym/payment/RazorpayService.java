package com.gym.payment;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;

import java.util.UUID;

public class RazorpayService {

    private final RazorpayClient client;

    public RazorpayService() throws RazorpayException {
        client = new RazorpayClient(
                RazorpayConfig.KEY_ID,
                RazorpayConfig.KEY_SECRET
        );
    }

    // Create Razorpay Payment Link
    public PaymentLink createPaymentLink(
            double amount,
            String packageName,
            String customerName,
            String customerEmail,
            String customerPhone
    ) throws RazorpayException {

        long amountInPaise = Math.round(amount * 100);

        String referenceId =
                "GYM-" +
                        UUID.randomUUID()
                                .toString()
                                .substring(0, 8)
                                .toUpperCase();

        JSONObject request = new JSONObject();

        request.put("amount", amountInPaise);
        request.put("currency", "INR");
        request.put("accept_partial", false);
        request.put("reference_id", referenceId);

        request.put(
                "description",
                "Gym Membership - " + packageName
        );

        request.put("reminder_enable", false);

        // Customer details
        JSONObject customer = new JSONObject();

        customer.put("name", customerName);
        customer.put("email", customerEmail);
        customer.put("contact", customerPhone);

        request.put("customer", customer);

        // Disable Razorpay notifications
        JSONObject notify = new JSONObject();

        notify.put("sms", false);
        notify.put("email", false);

        request.put("notify", notify);

        return client.paymentLink.create(request);
    }

    // Fetch an existing Payment Link
    public PaymentLink fetchPaymentLink(
            String paymentLinkId
    ) throws RazorpayException {

        return client.paymentLink.fetch(paymentLinkId);
    }

    // Check whether payment is successful
    public boolean isPaymentSuccessful(
            String paymentLinkId
    ) throws RazorpayException {

        PaymentLink link =
                fetchPaymentLink(paymentLinkId);

        String status =
                link.get("status");

        return "paid".equalsIgnoreCase(status);
    }

    // Get Payment Link ID
    public String getPaymentLinkId(
            PaymentLink link
    ) {

        return link.get("id");
    }

    // Get Payment URL
    public String getPaymentUrl(
            PaymentLink link
    ) {

        return link.get("short_url");
    }

    // Get Payment ID
    public String getPaymentId(
            PaymentLink link
    ) {

        try {

            Object paymentsObject =
                    link.get("payments");

            if (paymentsObject
                    instanceof org.json.JSONArray) {

                org.json.JSONArray payments =
                        (org.json.JSONArray) paymentsObject;

                if (payments.length() > 0) {

                    JSONObject payment =
                            payments.getJSONObject(0);

                    return payment.optString(
                            "payment_id",
                            null
                    );
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }
}