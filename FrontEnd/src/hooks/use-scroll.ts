import { useRef, useEffect, useCallback } from "react";

export interface ScrollFadeInOptions {
  direction: "up" | "down";
  duration: number;
  delay: number;
}

export const useScrollFadeIn = ({
  direction = "up",
  duration = 1,
  delay = 0,
}: ScrollFadeInOptions) => {
  const dom = useRef<HTMLDivElement>(null);
  const observer = useRef<IntersectionObserver | null>(null);

  const handleDirection = (name: "up" | "down"): string => {
    switch (name) {
      case "up":
        return "translate3d(0, 10%, 0)";
      case "down":
        return "translate3d(0, -10%, 0)";
      default:
        return "";
    }
  };

  const handleScroll = useCallback(
    ([entry]: IntersectionObserverEntry[]) => {
      const { current } = dom;
      if (entry.isIntersecting && current) {
        current.style.transitionProperty = "all";
        current.style.transitionDuration = `${duration}s`;
        current.style.transitionTimingFunction = "cubic-bezier(0, 0, 0.2, 1)";
        current.style.transitionDelay = `${delay}s`;
        current.style.opacity = "1";
        current.style.transform = "translate3d(0, 0, 0)";
      }
    },
    [delay, duration]
  );

  useEffect(() => {
    const { current } = dom;

    if (current) {
      observer.current = new IntersectionObserver(handleScroll, { threshold: 0.7 });
      observer.current.observe(current);

      return () => {
        if (observer.current) {
          observer.current.disconnect();
        }
      };
    }
  }, [handleScroll]);

  return {
    ref: dom,
    style: {
      opacity: 0.1,
      transform: handleDirection(direction),
    },
  };
};

export default useScrollFadeIn;
