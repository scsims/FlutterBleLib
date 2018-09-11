///
//  Generated code. Do not modify.
//  source: bledata.proto
///
// ignore_for_file: non_constant_identifier_names,library_prefixes,unused_import

const LogLevelMessage$json = const {
  '1': 'LogLevelMessage',
  '2': const [
    const {'1': 'VERBOSE', '2': 0},
    const {'1': 'DEBUG', '2': 1},
    const {'1': 'INFO', '2': 2},
    const {'1': 'WARNING', '2': 3},
    const {'1': 'ERROR', '2': 4},
    const {'1': 'NONE', '2': 5},
  ],
};

const BluetoothStateMessage$json = const {
  '1': 'BluetoothStateMessage',
  '2': const [
    const {'1': 'UNKNOWN', '2': 0},
    const {'1': 'RESETTING', '2': 1},
    const {'1': 'UNSUPPORTED', '2': 2},
    const {'1': 'UNAUTHORIZED', '2': 3},
    const {'1': 'POWERED_OFF', '2': 4},
    const {'1': 'POWERED_ON', '2': 5},
  ],
};

const ScanDataMessage$json = const {
  '1': 'ScanDataMessage',
  '2': const [
    const {'1': 'scanMode', '3': 1, '4': 1, '5': 5, '10': 'scanMode'},
    const {'1': 'callbackType', '3': 2, '4': 1, '5': 5, '10': 'callbackType'},
    const {'1': 'uuids', '3': 3, '4': 3, '5': 9, '10': 'uuids'},
  ],
};

const BleDeviceMessage$json = const {
  '1': 'BleDeviceMessage',
  '2': const [
    const {'1': 'id', '3': 1, '4': 1, '5': 9, '10': 'id'},
    const {'1': 'name', '3': 2, '4': 1, '5': 9, '10': 'name'},
    const {'1': 'rssi', '3': 3, '4': 1, '5': 5, '10': 'rssi'},
    const {'1': 'mtu', '3': 4, '4': 1, '5': 5, '10': 'mtu'},
    const {'1': 'isConnected', '3': 5, '4': 1, '5': 8, '10': 'isConnected'},
  ],
};

const ScanResultMessage$json = const {
  '1': 'ScanResultMessage',
  '2': const [
    const {'1': 'bleDeviceMessage', '3': 1, '4': 1, '5': 11, '6': '.BleDeviceMessage', '10': 'bleDeviceMessage'},
    const {'1': 'rssi', '3': 2, '4': 1, '5': 5, '10': 'rssi'},
    const {'1': 'timestampNanos', '3': 3, '4': 1, '5': 4, '10': 'timestampNanos'},
    const {'1': 'scanCallbackTypeMessage', '3': 4, '4': 1, '5': 5, '10': 'scanCallbackTypeMessage'},
  ],
};

const ConnectToDeviceDataMessage$json = const {
  '1': 'ConnectToDeviceDataMessage',
  '2': const [
    const {'1': 'macAddress', '3': 1, '4': 1, '5': 9, '10': 'macAddress'},
    const {'1': 'isAutoConnect', '3': 2, '4': 1, '5': 8, '10': 'isAutoConnect'},
    const {'1': 'requestMtu', '3': 3, '4': 1, '5': 5, '10': 'requestMtu'},
  ],
};

const ServiceMessages$json = const {
  '1': 'ServiceMessages',
  '2': const [
    const {'1': 'serviceMessages', '3': 1, '4': 3, '5': 11, '6': '.ServiceMessage', '10': 'serviceMessages'},
  ],
};

const ServiceMessage$json = const {
  '1': 'ServiceMessage',
  '2': const [
    const {'1': 'id', '3': 1, '4': 1, '5': 1, '10': 'id'},
    const {'1': 'uuid', '3': 2, '4': 1, '5': 9, '10': 'uuid'},
    const {'1': 'device', '3': 3, '4': 1, '5': 11, '6': '.BleDeviceMessage', '10': 'device'},
    const {'1': 'isPrimary', '3': 4, '4': 1, '5': 8, '10': 'isPrimary'},
  ],
};

const CharacteristicMessages$json = const {
  '1': 'CharacteristicMessages',
  '2': const [
    const {'1': 'characteristicMessage', '3': 1, '4': 3, '5': 11, '6': '.CharacteristicMessage', '10': 'characteristicMessage'},
  ],
};

const CharacteristicMessage$json = const {
  '1': 'CharacteristicMessage',
  '2': const [
    const {'1': 'id', '3': 1, '4': 1, '5': 1, '10': 'id'},
    const {'1': 'uuid', '3': 2, '4': 1, '5': 9, '10': 'uuid'},
    const {'1': 'serviceId', '3': 3, '4': 1, '5': 5, '10': 'serviceId'},
    const {'1': 'serviceUuid', '3': 4, '4': 1, '5': 9, '10': 'serviceUuid'},
    const {'1': 'deviceId', '3': 5, '4': 1, '5': 9, '10': 'deviceId'},
    const {'1': 'isReadable', '3': 6, '4': 1, '5': 8, '10': 'isReadable'},
    const {'1': 'isWritableWithResponse', '3': 7, '4': 1, '5': 8, '10': 'isWritableWithResponse'},
    const {'1': 'isWritableWithoutResponse', '3': 8, '4': 1, '5': 8, '10': 'isWritableWithoutResponse'},
    const {'1': 'isNotificable', '3': 9, '4': 1, '5': 8, '10': 'isNotificable'},
    const {'1': 'isIndicatable', '3': 10, '4': 1, '5': 8, '10': 'isIndicatable'},
    const {'1': 'isNotifing', '3': 11, '4': 1, '5': 8, '10': 'isNotifing'},
    const {'1': 'value', '3': 12, '4': 1, '5': 9, '10': 'value'},
  ],
};

const MonitorCharacteristicMessage$json = const {
  '1': 'MonitorCharacteristicMessage',
  '2': const [
    const {'1': 'transactionId', '3': 1, '4': 1, '5': 9, '10': 'transactionId'},
    const {'1': 'characteristicMessage', '3': 2, '4': 1, '5': 11, '6': '.CharacteristicMessage', '10': 'characteristicMessage'},
  ],
};

