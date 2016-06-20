(function() {
    'use strict';

    angular
        .module('fleetApp')
        .controller('PersonDetailController', PersonDetailController);

    PersonDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Person', 'Company'];

    function PersonDetailController($scope, $rootScope, $stateParams, entity, Person, Company) {
        var vm = this;

        vm.person = entity;

        var unsubscribe = $rootScope.$on('fleetApp:personUpdate', function(event, result) {
            vm.person = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
