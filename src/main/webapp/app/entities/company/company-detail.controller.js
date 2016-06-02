(function() {
    'use strict';

    angular
        .module('fleetApp')
        .controller('CompanyDetailController', CompanyDetailController);

    CompanyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Company'];

    function CompanyDetailController($scope, $rootScope, $stateParams, entity, Company) {
        var vm = this;

        vm.company = entity;

        var unsubscribe = $rootScope.$on('fleetApp:companyUpdate', function(event, result) {
            vm.company = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
