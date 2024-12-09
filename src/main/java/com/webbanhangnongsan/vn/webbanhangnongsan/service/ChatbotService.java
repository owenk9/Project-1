package com.webbanhangnongsan.vn.webbanhangnongsan.service;

import com.webbanhangnongsan.vn.webbanhangnongsan.entity.ChatMessage;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class ChatbotService {
    private final Map<String, String[]> responses = new HashMap<>() {{
        put("đơn hàng", new String[]{
                "Bạn có thể tra cứu đơn hàng tại mục 'Đơn hàng của tôi'.",
                "Để tra cứu đơn hàng, vui lòng cung cấp mã đơn hàng."
        });
        put("thanh toán", new String[]{
                "Chúng tôi hỗ trợ nhiều phương thức: Chuyển khoản, COD, MOMO.",
                "Thanh toán an toàn và bảo mật 100%"
        });
        put("giá", new String[]{
                "Giá sản phẩm từ 20.000đ đến 150.000đ tùy loại.",
                "Luôn có chương trình khuyến mãi hàng tuần!"
        });
    }};

    public ChatMessage processMessage(ChatMessage userMessage) {
        String response = generateResponse(userMessage.getContent());

        return new ChatMessage(
                "Chatbot Nông Sản",
                response,
                LocalDateTime.now()
        );
    }

    private String generateResponse(String userInput) {
        userInput = userInput.toLowerCase().trim();
        Random random = new Random();

        for (Map.Entry<String, String[]> entry : responses.entrySet()) {
            if (userInput.contains(entry.getKey())) {
                String[] possibleResponses = entry.getValue();
                return possibleResponses[random.nextInt(possibleResponses.length)];
            }
        }

        return "Xin chào! Tôi chưa hiểu rõ yêu cầu. Bạn có thể hỏi về đơn hàng, thanh toán hoặc giá cả.";
    }
}
