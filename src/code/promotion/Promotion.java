package code.promotion;
import java.time.LocalDate;
public abstract class Promotion implements Applicable{
    protected String promoCode;
    protected final LocalDate startDate = LocalDate.of(2024, 5, 9);
    protected final LocalDate endDate = LocalDate.of(2024, 6, 9);

}
