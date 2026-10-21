package software.ulpgc.kata3.model;

public record Employee(
        String name,
        Integer age,
        String lastWorkingDay,
        String department,
        String education,
        String gender,
        String maritalStatus,
        Integer monthlyIncome,
        Integer yearsOfExperience,
        Integer percentSalaryHike,
        Integer performanceRating
) {
}
