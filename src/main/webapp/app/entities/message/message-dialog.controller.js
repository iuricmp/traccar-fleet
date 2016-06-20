(function() {
    'use strict';

    angular
        .module('fleetApp')
        .controller('MessageDialogController', MessageDialogController);

    MessageDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Message', 'Device'];

    function MessageDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Message, Device) {
        var vm = this;

        vm.message = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.devices = Device.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.message.id !== null) {
                Message.update(vm.message, onSaveSuccess, onSaveError);
            } else {
                Message.save(vm.message, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('fleetApp:messageUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.messageTime = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
