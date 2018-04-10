function populateSelectWithOptions($select, data) {
    $select.html('');

    $.each(data, function (index, issue) {
        console.log(issue);
        $select.append('<option value="' + issue + '">' + issue + '</option>');
    });

    // enable the select control
    $select.prop('disabled', false);
}

function main() {
    $("#open-button").on("click", function () {
        $(this).hide();
        $("#open-form-cont").show();
    });

    $("#close-button").on("click", function () {
        $(this).hide();
        $("#stats").hide();
        $("#pos-button-cont").hide();
        $("#close-form-cont").show();
    });

    $("#cancel-close").on("click", function () {
        $("#close-button").show();
        $("#stats").show();
        $("#pos-button-cont").show();
        $("#close-form-cont").hide();
    });

    $("#cancel-open").on("click", function () {
        $("#open-form-cont").hide();
        $("#open-button").show();
    });

    $("#open-form").on("submit", function (e) {
        let openCash = $("#open-cash").val();
        if (!isFinite(openCash) || openCash === "") {
            e.preventDefault();
            alert("A nyitó összeg üres, vagy nem szám.");
        }
    });

    $("#add-income").on("click", function () {
        $("#issue").prop('disabled', true);
        $(".button-cont").hide();
        $("#income-form-cont").show();
        $.get("/get-issues", function(response, status){
            populateSelectWithOptions($("#issue"), response.transactionIssues);
        });
    });

    $("#cancel-income").on("click", function () {
        $("#income-form-cont").hide();
        $(".button-cont").show();
    });

    $("#add-expense").on("click", function () {
        $("#expense-issue").prop('disabled', true);
        $(".button-cont").hide();
        $("#expense-form-cont").show();
        $.get("/get-issues", function(response, status){
            populateSelectWithOptions($("#expense-issue"), response.transactionIssues);
        });
    });

    $("#cancel-expense").on("click", function () {
        $("#expense-form-cont").hide();
        $(".button-cont").show();
    });

    $("#close-form").on("submit", function (e) {
        let closeCash = $("#closeCash").val();
        let terminalBalance = $("#terminalBalance").val();
        let cassaBalance = $("#cassaBalance").val();
        let posBalance = $("#posBalance").val();
        if (!isFinite(closeCash) || closeCash === "" ||
            !isFinite(terminalBalance) || terminalBalance === "" ||
            !isFinite(cassaBalance) || cassaBalance === "" ||
            !isFinite(posBalance) || posBalance === "") {
            e.preventDefault();
            alert("Valamelyik összeg üres, vagy nem szám.");
        }
    });

}

$(document).ready(function () {
    main();
});

