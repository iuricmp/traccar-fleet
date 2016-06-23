(function() {
    'use strict';

    angular
        .module('fleetApp')
        .controller('MessageController', MessageController);

    MessageController.$inject = ['$filter','$scope', '$state', 'Message', 'ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants'];

    function MessageController ($filter, $scope, $state, Message, ParseLinks, AlertService, pagingParams, paginationConstants) {
        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.loadAll = loadAll;
        vm.itemsPerPage = paginationConstants.itemsPerPage;

        vm.fromDate = new Date();

        loadAll();

        function loadAll () {
            var dateFormat = 'yyyy-MM-dd';
            var fromDate = $filter('date')(vm.fromDate, dateFormat);
            Message.query({
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                sort: sort(),
                fromDate: fromDate
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.messages = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadPage (page) {
            vm.page = page;
            vm.transition();
        }

        function transition () {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }

    }
})();
