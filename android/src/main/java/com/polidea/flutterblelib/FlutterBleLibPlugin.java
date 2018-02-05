package com.polidea.flutterblelib;


import android.content.Context;
import android.util.Log;

import com.polidea.flutterblelib.chanelhandler.BluetoothStateHandler;
import com.polidea.flutterblelib.chanelhandler.DeviceConnectionChangedHandler;
import com.polidea.flutterblelib.chanelhandler.MonitorCharacteristicHandler;
import com.polidea.flutterblelib.chanelhandler.ScanDevicesHandler;
import com.polidea.flutterblelib.listener.EventDelegate;
import com.polidea.flutterblelib.listener.Namespace;
import com.polidea.flutterblelib.listener.OnErrorAction;
import com.polidea.flutterblelib.listener.OnSuccessAction;

import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

public class FlutterBleLibPlugin implements MethodCallHandler, EventDelegate {

    public static final String TAG = "FlutterBleLibPlugin";
    private ScanDevicesHandler scanDevicesHandler;
    private BluetoothStateHandler bluetoothStateHandler;
    private DeviceConnectionChangedHandler deviceConnectionChangedHandler;
    private MonitorCharacteristicHandler monitorCharacteristicHandler;

    private BleHelper bleHelper;


    private FlutterBleLibPlugin(Context context) {
        bleHelper = new BleHelper(context);
        scanDevicesHandler = new ScanDevicesHandler();
        bluetoothStateHandler = new BluetoothStateHandler();
        deviceConnectionChangedHandler = new DeviceConnectionChangedHandler();
        monitorCharacteristicHandler = new MonitorCharacteristicHandler();
    }

    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), Namespace.flutter_ble_lib);
        final EventChannel scanDevicesChannel = new EventChannel(registrar.messenger(), Namespace.flutter_ble_lib_scanDevices);
        final EventChannel bluetoothStateChanel = new EventChannel(registrar.messenger(), Namespace.flutter_ble_lib_stateChange);
        final EventChannel deviceConnectionChangedChanel = new EventChannel(registrar.messenger(), Namespace.flutter_ble_lib_deviceConnectionChange);
        final EventChannel monitorCharacteristicChanel = new EventChannel(registrar.messenger(), Namespace.flutter_ble_lib_monitorCharacteristicChange);
        final FlutterBleLibPlugin handler = new FlutterBleLibPlugin(registrar.activity().getApplicationContext());
        channel.setMethodCallHandler(handler);
        scanDevicesChannel.setStreamHandler(handler.scanDevicesHandler);
        bluetoothStateChanel.setStreamHandler(handler.bluetoothStateHandler);
        deviceConnectionChangedChanel.setStreamHandler(handler.deviceConnectionChangedHandler);
        monitorCharacteristicChanel.setStreamHandler(handler.monitorCharacteristicHandler);
    }

    @Override
    public void onMethodCall(MethodCall call, final Result result) {
        switch (call.method) {
            case BleMethod.createClient: {
                //TODO
                bleHelper.createClient(null);
                bleHelper.registerEventDelegate(this);
                return;
            }
            case BleMethod.destroyClient: {
                bleHelper.destroyClient();
                return;
            }
            case BleMethod.cancelTransaction: {
                bleHelper.cancelTransaction(call.arguments.toString());
                return;
            }
            case BleMethod.setLogLevel: {
                bleHelper.setLogLevel(BleData.LogLevelMessage.valueOf(call.arguments.toString()));
                return;
            }
            case BleMethod.logLevel: {
                bleHelper.logLevel(new OnSuccessAction<BleData.LogLevelMessage>() {
                    @Override
                    public void onSuccess(BleData.LogLevelMessage logLevelMessage) {
                        result.success(logLevelMessage.ordinal());
                    }
                });
                return;
            }
            case BleMethod.state: {
                bleHelper.state(new OnSuccessAction<BleData.BluetoothStateMessage>() {
                    @Override
                    public void onSuccess(BleData.BluetoothStateMessage success) {
                        result.success(success.ordinal());
                    }
                });
                return;
            }
            case BleMethod.startDeviceScan: {
                startDeviceScan(call, result);
                return;
            }
            case BleMethod.stopDeviceScan: {
                bleHelper.stopDeviceScan();
                return;
            }
            case BleMethod.requestMTUForDevice: {
                requestMTUForDevice(call, result);
                return;
            }
            case BleMethod.readRSSIForDevice: {
                readRSSIForDevice(call, result);
                return;
            }
            case BleMethod.cancelDeviceConnection: {
                cancelDeviceConnection(call, result);
                return;
            }
            case BleMethod.connectToDevice: {
                connectToDevice(call, result);
                return;
            }
            case BleMethod.isDeviceConnected: {
                isDeviceConnected(call, result);
                return;
            }
            case BleMethod.discoverAllServicesAndCharacteristicsForDevice: {
                discoverAllServicesAndCharacteristicsForDevice(call, result);
                return;
            }
            case BleMethod.servicesForDevice: {
                servicesForDevice(call, result);
                return;
            }
            case BleMethod.characteristicsForDevice: {
                characteristicsForDevice(call, result);
                return;
            }
            case BleMethod.characteristicsForService: {
                characteristicsForService(call, result);
                return;
            }
            case BleMethod.writeCharacteristicForDevice: {
                writeCharacteristicForDevice(call, result);
                return;
            }
            case BleMethod.writeCharacteristicForService: {
                writeCharacteristicForService(call, result);
                return;
            }
            case BleMethod.writeCharacteristic: {
                writeCharacteristic(call, result);
                return;
            }
            case BleMethod.readCharacteristicForService: {
                readCharacteristicForService(call, result);
                return;
            }
            case BleMethod.readCharacteristicForDevice: {
                readCharacteristicForDevice(call, result);
                return;
            }
            case BleMethod.readCharacteristic: {
                readCharacteristic(call, result);
                return;
            }
            case BleMethod.monitorCharacteristicForDevice: {
                monitorCharacteristicForDevice(call, result);
                return;
            }
            case BleMethod.monitorCharacteristicForService: {
                monitorCharacteristicForService(call, result);
                return;
            }
            case BleMethod.monitorCharacteristic: {
                monitorCharacteristic(call, result);
                return;
            }
            default:
                result.notImplemented();

        }
    }

    @Override
    public <T> void dispatchEvent(Event event, T value) {
        switch (event){
            case ScanEvent:
                scanDevicesHandler.handleScanDevice((BleData.ScanResultMessage) value);
                break;
            case ReadEvent:
                //TODO
                break;
            case StateChangeEvent:
                bluetoothStateHandler.handleBluetoothState((BleData.BluetoothStateMessage) value);
                break;
            case RestoreStateEvent:
                //TODO
                break;
            case DisconnectionEvent:
                deviceConnectionChangedHandler.handleBluetoothState((BleData.BleDeviceMessage) value);
                break;
        }
    }

    private void discoverAllServicesAndCharacteristicsForDevice(MethodCall call, final Result result) {
        bleHelper.discoverAllServicesAndCharacteristicsForDevice(call.arguments.toString(),
                new OnSuccessAction<BleData.BleDeviceMessage>() {
                    @Override
                    public void onSuccess(BleData.BleDeviceMessage bleDeviceMessage) {
                        result.success(bleDeviceMessage.toByteArray());
                    }
                },
                new DefaultOnErrorAction(result)
        );
    }

    private void cancelDeviceConnection(MethodCall call, final Result result) {
        bleHelper.cancelDeviceConnection(call.arguments.toString(),
                new OnSuccessAction<BleData.BleDeviceMessage>() {
                    @Override
                    public void onSuccess(BleData.BleDeviceMessage bleDeviceMessage) {
                        result.success(bleDeviceMessage.toByteArray());
                    }
                },
                new DefaultOnErrorAction(result)
        );

    }

    private void readRSSIForDevice(MethodCall call, final Result result) {

        final String deviceId = call.argument(ArgKey.deviceId);
        final String transactionId = call.argument(ArgKey.transactionId);
        bleHelper.readRSSIForDevice(deviceId, transactionId,
                new OnSuccessAction<BleData.BleDeviceMessage>() {
                    @Override
                    public void onSuccess(BleData.BleDeviceMessage bleDeviceMessage) {
                        result.success(bleDeviceMessage.toByteArray());
                    }
                },

                new DefaultOnErrorAction(result)
        );
    }

    private void requestMTUForDevice(MethodCall call, final Result result) {

        final String deviceId = call.argument(ArgKey.deviceId);
        final int mtu = call.argument(ArgKey.mtu);
        final String transactionId = call.argument(ArgKey.transactionId);
        bleHelper.requestMTUForDevice(deviceId, mtu, transactionId,
                new OnSuccessAction<BleData.BleDeviceMessage>() {
                    @Override
                    public void onSuccess(BleData.BleDeviceMessage bleDeviceMessage) {
                        result.success(bleDeviceMessage.toByteArray());
                    }
                },
                new DefaultOnErrorAction(result)
        );
    }

    private void startDeviceScan(MethodCall call, final Result result) {
        final byte[] scanResultMessageByte = call.arguments();
        bleHelper.startDeviceScan(scanResultMessageByte,
                new OnSuccessAction<Void>() {
                    @Override
                    public void onSuccess(Void ignoredValue) {
                        result.success(null);
                    }
                },

                new DefaultOnErrorAction(result)
        );
    }

    private void connectToDevice(final MethodCall call, final Result result) {
        final byte[] connectToDeviceDataMessageByte = call.arguments();
        bleHelper.connectToDevice(
                connectToDeviceDataMessageByte,
                new OnSuccessAction<BleData.BleDeviceMessage>() {
                    @Override
                    public void onSuccess(BleData.BleDeviceMessage connectedDeviceMessage) {
                        result.success(connectedDeviceMessage.toByteArray());
                    }
                },
                new DefaultOnErrorAction(result)
        );
    }

    private void isDeviceConnected(MethodCall call, final Result result) {
        final String macAddress = call.arguments();
        bleHelper.isDeviceConnected(
                macAddress,
                new OnSuccessAction<Boolean>() {
                    @Override
                    public void onSuccess(Boolean success) {
                        result.success(success);
                    }
                },
                new DefaultOnErrorAction(result)
        );
    }

    private void servicesForDevice(MethodCall call, final Result result) {
        final String macAddress = call.arguments();
        bleHelper.servicesForDevice(
                macAddress,
                new OnSuccessAction<BleData.ServiceMessages>() {
                    @Override
                    public void onSuccess(BleData.ServiceMessages serviceMessages) {
                        result.success(serviceMessages.toByteArray());
                    }
                },
                new DefaultOnErrorAction(result)
        );
    }

    private void characteristicsForDevice(MethodCall call, final Result result) {

        final String macAddressByte = call.argument(ArgKey.deviceId);
        final String serviceUUID = call.argument(ArgKey.serviceUUID);
        bleHelper.characteristicsForDevice(macAddressByte,
                serviceUUID,
                new OnSuccessAction<BleData.CharacteristicMessages>() {
                    @Override
                    public void onSuccess(BleData.CharacteristicMessages characteristicMessages) {
                        result.success(characteristicMessages.toByteArray());
                    }
                },
                new DefaultOnErrorAction(result)
        );
    }

    private void characteristicsForService(MethodCall call, final Result result) {
        bleHelper.characteristicsForService((Integer) call.arguments(),
                new OnSuccessAction<BleData.CharacteristicMessages>() {
                    @Override
                    public void onSuccess(BleData.CharacteristicMessages characteristicMessages) {
                        result.success(characteristicMessages.toByteArray());
                    }
                },
                new DefaultOnErrorAction(result)
        );
    }

    private void writeCharacteristicForDevice(MethodCall call, final Result result) {
        final String deviceId = call.argument(ArgKey.deviceId);
        final String serviceUUID = call.argument(ArgKey.serviceUUID);
        final String characteristicUUID = call.argument(ArgKey.characteristicUUID);
        final String valueBase64 = call.argument(ArgKey.valueBase64);
        final Boolean response = call.argument(ArgKey.response);
        final String transactionId = call.argument(ArgKey.transactionId);
        bleHelper.writeCharacteristicForDevice(
                deviceId,
                serviceUUID,
                characteristicUUID,
                valueBase64,
                response,
                transactionId,
                new OnSuccessAction<BleData.CharacteristicMessage>() {
                    @Override
                    public void onSuccess(BleData.CharacteristicMessage characteristicMessage) {
                        result.success(characteristicMessage.toByteArray());
                    }
                },
                new DefaultOnErrorAction(result)
        );
    }

    private void writeCharacteristicForService(MethodCall call, final Result result) {
        final int serviceIdentifier = call.argument(ArgKey.serviceIdentifier);
        final String characteristicUUID = call.argument(ArgKey.characteristicUUID);
        final String valueBase64 = call.argument(ArgKey.valueBase64);
        final Boolean response = call.argument(ArgKey.response);
        final String transactionId = call.argument(ArgKey.transactionId);
        bleHelper.writeCharacteristicForService(
                serviceIdentifier,
                characteristicUUID,
                valueBase64,
                response,
                transactionId,
                new OnSuccessAction<BleData.CharacteristicMessage>() {
                    @Override
                    public void onSuccess(BleData.CharacteristicMessage characteristicMessage) {
                        result.success(characteristicMessage.toByteArray());
                    }
                },
                new DefaultOnErrorAction(result)
        );
    }

    private void writeCharacteristic(MethodCall call, final Result result) {
        final int characteristicIdentifier = call.argument(ArgKey.characteristicIdentifier);
        final String valueBase64 = call.argument(ArgKey.valueBase64);
        final Boolean response = call.argument(ArgKey.response);
        final String transactionId = call.argument(ArgKey.transactionId);
        bleHelper.writeCharacteristic(
                characteristicIdentifier,
                valueBase64,
                response,
                transactionId,
                new OnSuccessAction<BleData.CharacteristicMessage>() {
                    @Override
                    public void onSuccess(BleData.CharacteristicMessage characteristicMessage) {
                        result.success(characteristicMessage.toByteArray());
                    }
                },
                new DefaultOnErrorAction(result)
        );
    }

    private void readCharacteristicForDevice(MethodCall call, final Result result) {
        final String deviceId = call.argument(ArgKey.deviceId);
        final String serviceUUID = call.argument(ArgKey.serviceUUID);
        final String characteristicUUID = call.argument(ArgKey.characteristicUUID);
        final String transactionId = call.argument(ArgKey.transactionId);
        bleHelper.readCharacteristicForDevice(
                deviceId,
                serviceUUID,
                characteristicUUID,
                transactionId,
                new OnSuccessAction<BleData.CharacteristicMessage>() {
                    @Override
                    public void onSuccess(BleData.CharacteristicMessage characteristicMessage) {
                        result.success(characteristicMessage.toByteArray());
                    }
                },
                new DefaultOnErrorAction(result)
        );
    }

    private void readCharacteristicForService(MethodCall call, final Result result) {
        final int serviceIdentifier = call.argument(ArgKey.serviceIdentifier);
        final String characteristicUUID = call.argument(ArgKey.characteristicUUID);
        final String transactionId = call.argument(ArgKey.transactionId);
        bleHelper.readCharacteristicForService(
                serviceIdentifier,
                characteristicUUID,
                transactionId,
                new OnSuccessAction<BleData.CharacteristicMessage>() {
                    @Override
                    public void onSuccess(BleData.CharacteristicMessage characteristicMessage) {
                        result.success(characteristicMessage.toByteArray());
                    }
                },
                new DefaultOnErrorAction(result)
        );
    }

    private void readCharacteristic(MethodCall call, final Result result) {
        final int characteristicIdentifier = call.argument(ArgKey.characteristicIdentifier);
        final String transactionId = call.argument(ArgKey.transactionId);
        bleHelper.readCharacteristic(
                characteristicIdentifier,
                transactionId,
                new OnSuccessAction<BleData.CharacteristicMessage>() {
                    @Override
                    public void onSuccess(BleData.CharacteristicMessage characteristicMessage) {
                        result.success(characteristicMessage.toByteArray());
                    }
                },
                new DefaultOnErrorAction(result)
        );
    }

    private void monitorCharacteristicForDevice(MethodCall call, final Result result) {
        final String deviceId = call.argument(ArgKey.deviceId);
        final String serviceUUID = call.argument(ArgKey.serviceUUID);
        final String characteristicUUID = call.argument(ArgKey.characteristicUUID);
        final String transactionId = call.argument(ArgKey.transactionId);
        bleHelper.monitorCharacteristicForDevice(
                deviceId,
                serviceUUID,
                characteristicUUID,
                transactionId,
                new VoidOnSuccessAction(result),
                new DefaultOnErrorAction(result)
        );
    }

    private void monitorCharacteristicForService(MethodCall call, final Result result) {
        final int serviceIdentifier = call.argument(ArgKey.serviceIdentifier);
        final String characteristicUUID = call.argument(ArgKey.characteristicUUID);
        final String transactionId = call.argument(ArgKey.transactionId);
        bleHelper.monitorCharacteristicForService(
                serviceIdentifier,
                characteristicUUID,
                transactionId,
                new VoidOnSuccessAction(result),
                new DefaultOnErrorAction(result)
        );
    }

    private void monitorCharacteristic(MethodCall call, final Result result) {
        final int characteristicIdentifier = call.argument(ArgKey.characteristicIdentifier);
        final String transactionId = call.argument(ArgKey.transactionId);
        bleHelper.monitorCharacteristic(
                characteristicIdentifier,
                transactionId,
                new VoidOnSuccessAction(result),
                new DefaultOnErrorAction(result)
        );
    }

    private static class DefaultOnErrorAction implements OnErrorAction {
        Result result;

        DefaultOnErrorAction(Result result) {
            this.result = result;
        }

        @Override
        public void onError(Throwable t) {
            result.error("Error occurred", t.getMessage(), t);
        }
    }

    private static class VoidOnSuccessAction implements OnSuccessAction<Void> {
        Result result;

        VoidOnSuccessAction(Result result) {
            this.result = result;
        }
        @Override
        public void onSuccess(Void ignoredMessage) {
            result.success(null);
        }
    }
}

