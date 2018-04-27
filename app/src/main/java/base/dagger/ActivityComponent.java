package base.dagger;

public interface ActivityComponent<A> {
    void inject(A activity);
}
