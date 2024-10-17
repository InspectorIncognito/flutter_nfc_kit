
import 'package:intl/intl.dart';
import 'package:json_annotation/json_annotation.dart';

part 'models.g.dart';

class UnknownMetroApiException implements Exception { }

class InvalidMetroApiException implements Exception { }

class NoNetworkException implements Exception { }

class NoActivationNeededException implements Exception { }

@JsonSerializable()
class ActivationData {
  final int wallet;
  final String cardId;
  final List<Product> products;

  ActivationData(this.wallet, this.cardId, this.products);

  factory ActivationData.fromJson(Map<String, dynamic> json) => _$ActivationDataFromJson(json);
  Map<String, dynamic> toJson() => _$ActivationDataToJson(this);
}

@JsonSerializable()
class Product {
  final int balance;
  final String? description;

  Product(this.balance, this.description);

  factory Product.fromJson(Map<String, dynamic> json) => _$ProductFromJson(json);
  Map<String, dynamic> toJson() => _$ProductToJson(this);

}

@JsonSerializable()
class CardData {
  final int serialNumber;
  final int balance;
  final List<ChargeEventData> chargeEventData;
  final List<ValidationEventData> validationEventData;

  CardData(this.serialNumber, this.balance, this.chargeEventData, this.validationEventData);

  factory CardData.fromJson(Map<String, dynamic> json) => _$CardDataFromJson(json);
  Map<String, dynamic> toJson() => _$CardDataToJson(this);

}

abstract class EventData {
  final DateTime date;
  final int balance;

  EventData(this.date, this.balance);
}

@JsonSerializable()
@CustomDateTimeConverter()
class ChargeEventData extends EventData {
  final String description;

  ChargeEventData(DateTime date, int balance, this.description) : super(date, balance);

  factory ChargeEventData.fromJson(Map<String, dynamic> json) => _$ChargeEventDataFromJson(json);
  Map<String, dynamic> toJson() => _$ChargeEventDataToJson(this);
}

@JsonSerializable()
@CustomDateTimeConverter()
class ValidationEventData extends EventData{
  ValidationEventData(DateTime date, int balance) : super(date, balance);

  factory ValidationEventData.fromJson(Map<String, dynamic> json) => _$ValidationEventDataFromJson(json);
  Map<String, dynamic> toJson() => _$ValidationEventDataToJson(this);
}


class CustomDateTimeConverter implements JsonConverter<DateTime, String> {
  const CustomDateTimeConverter();

  @override
  DateTime fromJson(String json) {
    return DateFormat("dd-MM-yyyy HH:mm:ss").parse(json);
    
  }

  @override
  String toJson(DateTime json) => DateFormat("dd-MM-yyyy HH:mm:ss").format(json);
}