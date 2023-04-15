class BankAccount {

    protected int amount;
    protected String currency;

    protected String accountType;


    public void replenishBalance(int amount) {
        this.amount += amount;
        System.out.println("Счет пополнен на " + amount + " " + currency);
    }

    public void withdrawCash(int amount) {
    }

    public void showBalance() {
    }
}

class DebitAccount extends BankAccount {

    public DebitAccount(int amount, String currency) {
        if (amount < 0) {
            System.out.println("Баланс дебетового счета не может быть меньше 0");
        } else {
            this.amount = amount;
            this.currency = currency;
        }
    }

    @Override
    public void withdrawCash(int amount) {
        if (amount > this.amount) {
            System.out.println("У вас недостаточно средств для снятия суммы " + amount + " " + currency);
        } else {
            this.amount -= amount;
            System.out.println("Вы сняли " + amount + " " + currency);
        }
    }

    @Override
    public void showBalance() {
        System.out.println("На вашем счету осталось " + amount + " " + currency);
    }
}

class CreditAccount extends BankAccount {

    public int creditLimit;

    public CreditAccount(int amount, String currency, int creditLimit) {
        this.amount = amount;
        this.currency = currency;
        this.creditLimit = creditLimit;
    }

    @Override
    public void withdrawCash(int amount) {
        if (this.amount - amount < -creditLimit) {
            System.out.println("У вас недостаточно средств для снятия суммы " + amount + " " + currency);
        } else {
            this.amount -= amount;
            System.out.println("Вы сняли " + amount + " " + currency);
        }
    }

    @Override
    public void showBalance() {
        if (amount >= 0) {
            System.out.println("На вашем счету " + amount + " " + currency);
        } else {
            System.out.println("Ваша задолженность по кредитному счету составялет " + Math.abs(amount) + currency);
        }
    }
}

class Bank extends BankAccount {

    public BankAccount createNewAccount(String accountType, String currency) {

        if (accountType.equals("debit_account")) {
            System.out.println("Ваш дебетовый счет создан");
            DebitAccount debit = new DebitAccount(0, currency);
            return debit;
        } else if (accountType.equals("credit_account")) {
            int limit = creditLimit(currency);
            CreditAccount credit = new CreditAccount(0, currency, limit);
            System.out.println("Кредитный счет создан. Ваш лимит по счету " + limit + " " + currency);
            return credit;
        } else {
            BankAccount other = new BankAccount();
            System.out.println("Неверно указа тип создаваемого счёта");
            return other;
        }
    }

    public void closeAccount(BankAccount accountType) {

        if (accountType instanceof DebitAccount) {
            if (this.amount > 0) {
                System.out.println("Ваш дебетовый счёт закрыт. Вы можете получить остаток по вашему счёту в размере "
                        + this.amount + " " + this.currency + " в отделении банка");
            } else if (this.amount == 0) {
                System.out.println("Ваш дебетовый счет закрыт");
            }
        } else if (accountType instanceof CreditAccount) {
            if (this.amount == 0) {
                System.out.println("Ваш кредитный счёт закрыт");
            } else if (this.amount > 0) {
                System.out.println("Ваш кредитный счёт закрыт. Вы можете получить остаток по вашему счёту в размере "
                        + this.amount + " " + this.currency + " в отделении банка");
            } else if (this.amount < 0) {
                System.out.println("Вы не можете закрыть кредитный счёт, потому что на нём есть задолженность. Задолженность по счёту составляет " + Math.abs(this.amount) + " " + this.currency);
            }
        } else {
            System.out.println("Пока что мы не можем закрыть данный вид счёта");
        }
    }



    private int creditLimit (String currency) {
        int limit;
        if (currency.equals("RUB")) {
            return limit = 100000;
        } else if (currency.equals("USD")) {
            return limit = 1250;
        } else if (currency.equals("EUR")) {
            return 1000;
        } else {
            return 0;
        }
    }
}
