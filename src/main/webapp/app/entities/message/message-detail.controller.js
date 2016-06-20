(function() {
    'use strict';

    angular
        .module('fleetApp')
        .controller('MessageDetailController', MessageDetailController);

    MessageDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Message', 'Device'];

    function MessageDetailController($scope, $rootScope, $stateParams, entity, Message, Device) {
        var vm = this;

        vm.message = entity;

        var unsubscribe = $rootScope.$on('fleetApp:messageUpdate', function(event, result) {
            vm.message = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
