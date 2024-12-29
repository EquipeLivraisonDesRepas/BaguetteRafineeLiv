    package com.fooddelivery.fooddeliveryapp.entities;

    import jakarta.persistence.*;
    import jakarta.validation.constraints.Email;
    import jakarta.validation.constraints.NotBlank;
    import lombok.Getter;
    import lombok.Setter;

    import java.util.List;

    @Setter
    @Getter
    @Entity
    @Table(name = "users")
    public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotBlank(message = "Name is mandatory")
        private String name;

        @Email(message = "Email should be valid")
        @NotBlank(message = "Email is mandatory")
        @Column(unique = true)
        private String email;

        @NotBlank(message = "Password is mandatory")
        private String password;

        private String phone;

        @NotBlank(message = "Role is mandatory")
        private String role;

        @ManyToMany
        @JoinTable(
                name = "user_orders",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "order_id")
        )
        private List<Order> orders;

    }
