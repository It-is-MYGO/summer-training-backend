package modules.auth.entity;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String username;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @Column(name = "register_date")
    private LocalDateTime registerDate;
    
    @Column(name = "is_enabled")
    private Boolean isEnabled = false;
    
    @Column(name = "verification_code")
    private String verificationCode;
    
    // 构造方法、getter和setter
}