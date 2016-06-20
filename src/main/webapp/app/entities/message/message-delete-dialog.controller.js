(function() {
    'use strict';

    angular
        .module('fleetApp')
        .controller('MessageDeleteController',MessageDeleteController);

    MessageDeleteController.$inject = ['$uibModalInstance', 'entity', 'Message'];

    function MessageDeleteController($uibModalInstance, entity, Message) {
        var vm = this;

        vm.message = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Message.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
