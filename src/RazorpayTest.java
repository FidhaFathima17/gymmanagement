import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;

public class RazorpayTest {

    public static void main(String[] args) {

        String keyId = "rzp_test_TkKTgTG76sWaNn";
        String keySecret = "1RqwVNw3JzW2anm97fOq5t5m";

        try {

            RazorpayClient razorpay =
                    new RazorpayClient(keyId, keySecret);

            JSONObject orderRequest = new JSONObject();

            // ₹100 = 10000 paise
            orderRequest.put("amount", 10000);
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "gym_test_001");

            Order order = razorpay.orders.create(orderRequest);

            System.out.println("=================================");
            System.out.println("RAZORPAY TEST ORDER CREATED");
            System.out.println("=================================");

            System.out.println("Order ID: " + order.get("id"));
            System.out.println("Amount: ₹100");
            System.out.println("Currency: " + order.get("currency"));
            System.out.println("Status: " + order.get("status"));

        } catch (Exception e) {

            System.out.println("=================================");
            System.out.println("RAZORPAY ORDER CREATION FAILED");
            System.out.println("=================================");

            e.printStackTrace();
        }
    }
}