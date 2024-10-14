// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'models.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

CardData _$CardDataFromJson(Map<String, dynamic> json) => CardData(
      (json['serialNumber'] as num).toInt(),
      (json['balance'] as num).toInt(),
      (json['chargeEventData'] as List<dynamic>)
          .map((e) => ChargeEventData.fromJson(e as Map<String, dynamic>))
          .toList(),
      (json['validationEventData'] as List<dynamic>)
          .map((e) => ValidationEventData.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$CardDataToJson(CardData instance) => <String, dynamic>{
      'serialNumber': instance.serialNumber,
      'balance': instance.balance,
      'chargeEventData': instance.chargeEventData,
      'validationEventData': instance.validationEventData,
    };

ChargeEventData _$ChargeEventDataFromJson(Map<String, dynamic> json) =>
    ChargeEventData(
      const CustomDateTimeConverter().fromJson(json['date'] as String),
      (json['balance'] as num).toInt(),
      json['description'] as String,
    );

Map<String, dynamic> _$ChargeEventDataToJson(ChargeEventData instance) =>
    <String, dynamic>{
      'date': const CustomDateTimeConverter().toJson(instance.date),
      'balance': instance.balance,
      'description': instance.description,
    };

ValidationEventData _$ValidationEventDataFromJson(Map<String, dynamic> json) =>
    ValidationEventData(
      const CustomDateTimeConverter().fromJson(json['date'] as String),
      (json['balance'] as num).toInt(),
    );

Map<String, dynamic> _$ValidationEventDataToJson(
        ValidationEventData instance) =>
    <String, dynamic>{
      'date': const CustomDateTimeConverter().toJson(instance.date),
      'balance': instance.balance,
    };
