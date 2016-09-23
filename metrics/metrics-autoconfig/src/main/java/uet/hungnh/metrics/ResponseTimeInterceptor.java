package uet.hungnh.metrics;

import com.codahale.metrics.Histogram;
import com.codahale.metrics.Timer;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResponseTimeInterceptor extends HandlerInterceptorAdapter {

    private final ThreadLocal<Timer.Context> timerContextHolder = new ThreadLocal<>();

    private Timer responseTimer;

    public ResponseTimeInterceptor(Timer responseTimer) {
        this.responseTimer = responseTimer;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        timerContextHolder.set(responseTimer.time());
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        timerContextHolder.get().stop();
    }
}
