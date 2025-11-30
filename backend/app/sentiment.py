from transformers import pipeline, AutoTokenizer, AutoModelForSequenceClassification
import torch


class SentimentAnalyzer:
    def __init__(self):
        # Use FinBERT for financial sentiment
        model_name = "ProsusAI/finbert"
        self.tokenizer = AutoTokenizer.from_pretrained(model_name)
        self.model = AutoModelForSequenceClassification.from_pretrained(model_name)
        self.sentiment_pipeline = pipeline(
            "sentiment-analysis", model=self.model, tokenizer=self.tokenizer
        )

    def analyze_sentiment(self, text):
        """Analyze sentiment of financial text"""
        try:
            result = self.sentiment_pipeline(text)

            # Convert to numerical score (-1 to 1)
            label = result[0]["label"].lower()
            confidence = result[0]["score"]

            if label == "positive":
                return confidence
            elif label == "negative":
                return -confidence
            else:  # neutral
                return 0

        except Exception as e:
            print(f"Error analyzing sentiment: {e}")
            return 0
