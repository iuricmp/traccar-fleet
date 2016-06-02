(function() {
    'use strict';

    angular
        .module('fleetApp')
        .controller('DeviceDetailController', DeviceDetailController);

    DeviceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Device', 'Company'];

    function DeviceDetailController($scope, $rootScope, $stateParams, entity, Device, Company) {
        var vm = this;

        vm.device = entity;

        var unsubscribe = $rootScope.$on('fleetApp:deviceUpdate', function(event, result) {
            vm.device = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
