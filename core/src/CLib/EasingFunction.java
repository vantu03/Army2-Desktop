package CLib;

import model.CRes;

public class EasingFunction {
    private static float NATURAL_LOG_OF_2 = 0.6931472F;

    public static float Linear(float start, float end, float value) {
        return CRes.Lerp(start, end, value);
    }

    public static float Spring(float start, float end, float value) {
        value = CRes.Clamp01(value);
        value = (float) ((Math.sin((double) (value * 3.14F * (0.2F + 2.5F * value * value * value))) * Math.pow((double) (1.0F - value), 2.200000047683716D) + (double) value) * (double) (1.0F + 1.2F * (1.0F - value)));
        return start + (end - start) * value;
    }

    public static float EaseInQuad(float start, float end, float value) {
        end -= start;
        return end * value * value + start;
    }

    public static float EaseOutQuad(float start, float end, float value) {
        end -= start;
        return -end * value * (value - 2.0F) + start;
    }

    public static float EaseInOutQuad(float start, float end, float value) {
        value /= 0.5F;
        end -= start;
        if (value < 1.0F) {
            return end * 0.5F * value * value + start;
        } else {
            --value;
            return -end * 0.5F * (value * (value - 2.0F) - 1.0F) + start;
        }
    }

    public static float EaseInCubic(float start, float end, float value) {
        end -= start;
        return end * value * value * value + start;
    }

    public static float EaseOutCubic(float start, float end, float value) {
        --value;
        end -= start;
        return end * (value * value * value + 1.0F) + start;
    }

    public static float EaseInOutCubic(float start, float end, float value) {
        value /= 0.5F;
        end -= start;
        if (value < 1.0F) {
            return end * 0.5F * value * value * value + start;
        } else {
            value -= 2.0F;
            return end * 0.5F * (value * value * value + 2.0F) + start;
        }
    }

    public static float EaseInQuart(float start, float end, float value) {
        end -= start;
        return end * value * value * value * value + start;
    }

    public static float EaseOutQuart(float start, float end, float value) {
        --value;
        end -= start;
        return -end * (value * value * value * value - 1.0F) + start;
    }

    public static float EaseInOutQuart(float start, float end, float value) {
        value /= 0.5F;
        end -= start;
        if (value < 1.0F) {
            return end * 0.5F * value * value * value * value + start;
        } else {
            value -= 2.0F;
            return -end * 0.5F * (value * value * value * value - 2.0F) + start;
        }
    }

    public static float EaseInQuint(float start, float end, float value) {
        end -= start;
        return end * value * value * value * value * value + start;
    }

    public static float EaseOutQuint(float start, float end, float value) {
        --value;
        end -= start;
        return end * (value * value * value * value * value + 1.0F) + start;
    }

    public static float EaseInOutQuint(float start, float end, float value) {
        value /= 0.5F;
        end -= start;
        if (value < 1.0F) {
            return end * 0.5F * value * value * value * value * value + start;
        } else {
            value -= 2.0F;
            return end * 0.5F * (value * value * value * value * value + 2.0F) + start;
        }
    }

    public static float EaseInSine(float start, float end, float value) {
        end -= start;
        return (float) ((double) (-end) * Math.cos((double) value * 1.5707963267948966D) + (double) end + (double) start);
    }

    public static float EaseOutSine(float start, float end, float value) {
        end -= start;
        return (float) ((double) end * Math.sin((double) value * 1.5707963267948966D) + (double) start);
    }

    public static float EaseInOutSine(float start, float end, float value) {
        end -= start;
        return (float) ((double) (-end * 0.5F) * (Math.cos(3.141592653589793D * (double) value) - 1.0D) + (double) start);
    }

    public static float EaseInExpo(float start, float end, float value) {
        end -= start;
        return (float) ((double) end * Math.pow(2.0D, (double) (10.0F * (value - 1.0F))) + (double) start);
    }

    public static float EaseOutExpo(float start, float end, float value) {
        end -= start;
        return (float) ((double) end * (-Math.pow(2.0D, (double) (-10.0F * value)) + 1.0D) + (double) start);
    }

    public static float EaseInOutExpo(float start, float end, float value) {
        value /= 0.5F;
        end -= start;
        if (value < 1.0F) {
            return (float) ((double) (end * 0.5F) * Math.pow(2.0D, (double) (10.0F * (value - 1.0F))) + (double) start);
        } else {
            --value;
            return (float) ((double) (end * 0.5F) * (-Math.pow(2.0D, (double) (-10.0F * value)) + 2.0D) + (double) start);
        }
    }

    public static float EaseInCirc(float start, float end, float value) {
        end -= start;
        return (float) ((double) (-end) * (Math.sqrt((double) (1.0F - value * value)) - 1.0D) + (double) start);
    }

    public static float EaseOutCirc(float start, float end, float value) {
        --value;
        end -= start;
        return (float) ((double) end * Math.sqrt((double) (1.0F - value * value)) + (double) start);
    }

    public static float EaseInOutCirc(float start, float end, float value) {
        value /= 0.5F;
        end -= start;
        if (value < 1.0F) {
            return (float) ((double) (-end * 0.5F) * (Math.sqrt((double) (1.0F - value * value)) - 1.0D) + (double) start);
        } else {
            value -= 2.0F;
            return (float) ((double) (end * 0.5F) * (Math.sqrt((double) (1.0F - value * value)) + 1.0D) + (double) start);
        }
    }

    public static float EaseInBounce(float start, float end, float value) {
        end -= start;
        float d = 1.0F;
        return end - EaseOutBounce(0.0F, end, d - value) + start;
    }

    public static float EaseOutBounce(float start, float end, float value) {
        value /= 1.0F;
        end -= start;
        if (value < 0.36363637F) {
            return end * 7.5625F * value * value + start;
        } else if (value < 0.72727275F) {
            value -= 0.54545456F;
            return end * (7.5625F * value * value + 0.75F) + start;
        } else if ((double) value < 0.9090909090909091D) {
            value -= 0.8181818F;
            return end * (7.5625F * value * value + 0.9375F) + start;
        } else {
            value -= 0.95454544F;
            return end * (7.5625F * value * value + 0.984375F) + start;
        }
    }

    public static float EaseInOutBounce(float start, float end, float value) {
        end -= start;
        float d = 1.0F;
        return value < d * 0.5F ? EaseInBounce(0.0F, end, value * 2.0F) * 0.5F + start : EaseOutBounce(0.0F, end, value * 2.0F - d) * 0.5F + end * 0.5F + start;
    }

    public static float EaseInBack(float start, float end, float value) {
        end -= start;
        value /= 1.0F;
        float s = 1.70158F;
        return end * value * value * ((s + 1.0F) * value - s) + start;
    }

    public static float EaseOutBack(float start, float end, float value) {
        float s = 1.70158F;
        end -= start;
        --value;
        return end * (value * value * ((s + 1.0F) * value + s) + 1.0F) + start;
    }

    public static float EaseInOutBack(float start, float end, float value) {
        float s = 1.70158F;
        end -= start;
        value /= 0.5F;
        if (value < 1.0F) {
            s *= 1.525F;
            return end * 0.5F * value * value * ((s + 1.0F) * value - s) + start;
        } else {
            value -= 2.0F;
            s *= 1.525F;
            return end * 0.5F * (value * value * ((s + 1.0F) * value + s) + 2.0F) + start;
        }
    }

    public static float EaseInElastic(float start, float end, float value) {
        end -= start;
        float d = 1.0F;
        float p = d * 0.3F;
        float a = 0.0F;
        if (value == 0.0F) {
            return start;
        } else if ((value /= d) == 1.0F) {
            return start + end;
        } else {
            float s;
            if (a != 0.0F && !(a < Math.abs(end))) {
                s = (float) ((double) p / 6.283185307179586D * Math.asin((double) (end / a)));
            } else {
                a = end;
                s = p / 4.0F;
            }

            return (float) (-((double) a * Math.pow(2.0D, (double) (10.0F * --value)) * Math.sin((double) (value * d - s) * 6.283185307179586D / (double) p)) + (double) start);
        }
    }

    public static float EaseOutElastic(float start, float end, float value) {
        end -= start;
        float d = 1.0F;
        float p = d * 0.3F;
        float a = 0.0F;
        if (value == 0.0F) {
            return start;
        } else if ((value /= d) == 1.0F) {
            return start + end;
        } else {
            float s;
            if (a != 0.0F && !(a < Math.abs(end))) {
                s = (float) ((double) p / 6.283185307179586D * Math.asin((double) (end / a)));
            } else {
                a = end;
                s = p * 0.25F;
            }

            return (float) ((double) a * Math.pow(2.0D, (double) (-10.0F * value)) * Math.sin((double) (value * d - s) * 6.283185307179586D / (double) p) + (double) end + (double) start);
        }
    }

    public static float EaseInOutElastic(float start, float end, float value) {
        end -= start;
        float d = 1.0F;
        float p = d * 0.3F;
        float a = 0.0F;
        if (value == 0.0F) {
            return start;
        } else if ((value /= d * 0.5F) == 2.0F) {
            return start + end;
        } else {
            float s;
            if (a != 0.0F && !(a < Math.abs(end))) {
                s = (float) ((double) p / 6.283185307179586D * Math.asin((double) (end / a)));
            } else {
                a = end;
                s = p / 4.0F;
            }

            return value < 1.0F ? (float) (-0.5D * (double) a * Math.pow(2.0D, (double) (10.0F * --value)) * Math.sin((double) (value * d - s) * 6.283185307179586D / (double) p) + (double) start) : (float) ((double) a * Math.pow(2.0D, (double) (-10.0F * --value)) * Math.sin((double) (value * d - s) * 6.283185307179586D / (double) p) * 0.5D + (double) end + (double) start);
        }
    }

    public static float LinearD(float start, float end, float value) {
        return end - start;
    }

    public static float EaseInQuadD(float start, float end, float value) {
        return 2.0F * (end - start) * value;
    }

    public static float EaseOutQuadD(float start, float end, float value) {
        end -= start;
        return -end * value - end * (value - 2.0F);
    }

    public static float EaseInOutQuadD(float start, float end, float value) {
        value /= 0.5F;
        end -= start;
        if (value < 1.0F) {
            return end * value;
        } else {
            --value;
            return end * (1.0F - value);
        }
    }

    public static float EaseInCubicD(float start, float end, float value) {
        return 3.0F * (end - start) * value * value;
    }

    public static float EaseOutCubicD(float start, float end, float value) {
        --value;
        end -= start;
        return 3.0F * end * value * value;
    }

    public static float EaseInOutCubicD(float start, float end, float value) {
        value /= 0.5F;
        end -= start;
        if (value < 1.0F) {
            return 1.5F * end * value * value;
        } else {
            value -= 2.0F;
            return 1.5F * end * value * value;
        }
    }

    public static float EaseInQuartD(float start, float end, float value) {
        return 4.0F * (end - start) * value * value * value;
    }

    public static float EaseOutQuartD(float start, float end, float value) {
        --value;
        end -= start;
        return -4.0F * end * value * value * value;
    }

    public static float EaseInOutQuartD(float start, float end, float value) {
        value /= 0.5F;
        end -= start;
        if (value < 1.0F) {
            return 2.0F * end * value * value * value;
        } else {
            value -= 2.0F;
            return -2.0F * end * value * value * value;
        }
    }

    public static float EaseInQuintD(float start, float end, float value) {
        return 5.0F * (end - start) * value * value * value * value;
    }

    public static float EaseOutQuintD(float start, float end, float value) {
        --value;
        end -= start;
        return 5.0F * end * value * value * value * value;
    }

    public static float EaseInOutQuintD(float start, float end, float value) {
        value /= 0.5F;
        end -= start;
        if (value < 1.0F) {
            return 2.5F * end * value * value * value * value;
        } else {
            value -= 2.0F;
            return 2.5F * end * value * value * value * value;
        }
    }

    public static float EaseInSineD(float start, float end, float value) {
        return (float) ((double) ((end - start) * 0.5F) * 3.141592653589793D * Math.sin(1.5707963267948966D * (double) value));
    }

    public static float EaseOutSineD(float start, float end, float value) {
        end -= start;
        return (float) (1.5707963267948966D * (double) end * Math.cos((double) value * 1.5707963267948966D));
    }

    public static float EaseInOutSineD(float start, float end, float value) {
        end -= start;
        return (float) ((double) (end * 0.5F) * 3.141592653589793D * Math.sin(3.141592653589793D * (double) value));
    }

    public static float EaseInExpoD(float start, float end, float value) {
        return (float) ((double) (10.0F * NATURAL_LOG_OF_2 * (end - start)) * Math.pow(2.0D, (double) (10.0F * (value - 1.0F))));
    }

    public static float EaseOutExpoD(float start, float end, float value) {
        end -= start;
        return (float) ((double) (5.0F * NATURAL_LOG_OF_2 * end) * Math.pow(2.0D, (double) (1.0F - 10.0F * value)));
    }

    public static float EaseInOutExpoD(float start, float end, float value) {
        value /= 0.5F;
        end -= start;
        if (value < 1.0F) {
            return (float) ((double) (5.0F * NATURAL_LOG_OF_2 * end) * Math.pow(2.0D, (double) (10.0F * (value - 1.0F))));
        } else {
            --value;
            return (float) ((double) (5.0F * NATURAL_LOG_OF_2 * end) / Math.pow(2.0D, (double) (10.0F * value)));
        }
    }

    public static float EaseInCircD(float start, float end, float value) {
        return (float) ((double) ((end - start) * value) / Math.sqrt((double) (1.0F - value * value)));
    }

    public static float EaseOutCircD(float start, float end, float value) {
        --value;
        end -= start;
        return (float) ((double) (-end * value) / Math.sqrt((double) (1.0F - value * value)));
    }

    public static float EaseInOutCircD(float start, float end, float value) {
        value /= 0.5F;
        end -= start;
        if (value < 1.0F) {
            return (float) ((double) (end * value) / (2.0D * Math.sqrt((double) (1.0F - value * value))));
        } else {
            value -= 2.0F;
            return (float) ((double) (-end * value) / (2.0D * Math.sqrt((double) (1.0F - value * value))));
        }
    }

    public static float EaseInBounceD(float start, float end, float value) {
        end -= start;
        float d = 1.0F;
        return EaseOutBounceD(0.0F, end, d - value);
    }

    public static float EaseOutBounceD(float start, float end, float value) {
        value /= 1.0F;
        end -= start;
        if (value < 0.36363637F) {
            return 2.0F * end * 7.5625F * value;
        } else if (value < 0.72727275F) {
            value -= 0.54545456F;
            return 2.0F * end * 7.5625F * value;
        } else if ((double) value < 0.9090909090909091D) {
            value -= 0.8181818F;
            return 2.0F * end * 7.5625F * value;
        } else {
            value -= 0.95454544F;
            return 2.0F * end * 7.5625F * value;
        }
    }

    public static float EaseInOutBounceD(float start, float end, float value) {
        end -= start;
        float d = 1.0F;
        return value < d * 0.5F ? EaseInBounceD(0.0F, end, value * 2.0F) * 0.5F : EaseOutBounceD(0.0F, end, value * 2.0F - d) * 0.5F;
    }

    public static float EaseInBackD(float start, float end, float value) {
        float s = 1.70158F;
        return 3.0F * (s + 1.0F) * (end - start) * value * value - 2.0F * s * (end - start) * value;
    }

    public static float EaseOutBackD(float start, float end, float value) {
        float s = 1.70158F;
        end -= start;
        --value;
        return end * ((s + 1.0F) * value * value + 2.0F * value * ((s + 1.0F) * value + s));
    }

    public static float EaseInOutBackD(float start, float end, float value) {
        float s = 1.70158F;
        end -= start;
        value /= 0.5F;
        if (value < 1.0F) {
            s *= 1.525F;
            return 0.5F * end * (s + 1.0F) * value * value + end * value * ((s + 1.0F) * value - s);
        } else {
            value -= 2.0F;
            s *= 1.525F;
            return 0.5F * end * ((s + 1.0F) * value * value + 2.0F * value * ((s + 1.0F) * value + s));
        }
    }

    public static float EaseInElasticD(float start, float end, float value) {
        return EaseOutElasticD(start, end, 1.0F - value);
    }

    public static float EaseOutElasticD(float start, float end, float value) {
        end -= start;
        float d = 1.0F;
        float p = d * 0.3F;
        float a = 0.0F;
        float s;
        if (a != 0.0F && !(a < Math.abs(end))) {
            s = (float) ((double) p / 6.283185307179586D * Math.asin((double) (end / a)));
        } else {
            a = end;
            s = p * 0.25F;
        }

        return (float) ((double) a * 3.141592653589793D * (double) d * Math.pow(2.0D, (double) (1.0F - 10.0F * value)) * Math.cos(6.283185307179586D * (double) (d * value - s) / (double) p) / (double) p - (double) (5.0F * NATURAL_LOG_OF_2 * a) * Math.pow(2.0D, (double) (1.0F - 10.0F * value)) * Math.sin(6.283185307179586D * (double) (d * value - s) / (double) p));
    }

    public static float EaseInOutElasticD(float start, float end, float value) {
        end -= start;
        float d = 1.0F;
        float p = d * 0.3F;
        float a = 0.0F;
        float s;
        if (a != 0.0F && !(a < Math.abs(end))) {
            s = (float) ((double) p / 6.283185307179586D * Math.asin((double) (end / a)));
        } else {
            a = end;
            s = p / 4.0F;
        }

        if (value < 1.0F) {
            --value;
            return (float) ((double) (-5.0F * NATURAL_LOG_OF_2 * a) * Math.pow(2.0D, (double) (10.0F * value)) * Math.sin(6.283185307179586D * (double) (d * value - 2.0F) / (double) p) - (double) a * 3.141592653589793D * (double) d * Math.pow(2.0D, (double) (10.0F * value)) * Math.cos(6.283185307179586D * (double) (d * value - s) / (double) p) / (double) p);
        } else {
            --value;
            return (float) ((double) a * 3.141592653589793D * (double) d * Math.cos(6.283185307179586D * (double) (d * value - s) / (double) p) / ((double) p * Math.pow(2.0D, (double) (10.0F * value))) - (double) (5.0F * NATURAL_LOG_OF_2 * a) * Math.sin(6.283185307179586D * (double) (d * value - s) / (double) p) / Math.pow(2.0D, (double) (10.0F * value)));
        }
    }

    public static float SpringD(float start, float end, float value) {
        value = CRes.Clamp01(value);
        end -= start;
        return (float) ((double) (end * (6.0F * (1.0F - value) / 5.0F + 1.0F)) * (-2.200000047683716D * Math.pow((double) (1.0F - value), 1.2000000476837158D) * Math.sin(3.141592653589793D * (double) value * (double) (2.5F * value * value * value + 0.2F)) + Math.pow((double) (1.0F - value), 2.200000047683716D) * (3.141592653589793D * (double) (2.5F * value * value * value + 0.2F) + 23.561944901923447D * (double) value * (double) value * (double) value) * Math.cos(3.141592653589793D * (double) value * (double) (2.5F * value * value * value + 0.2F)) + 1.0D) - (double) (6.0F * end) * (Math.pow((double) (1.0F - value), 2.200000047683716D) * Math.sin(3.141592653589793D * (double) value * (double) (2.5F * value * value * value + 0.2F)) + (double) (value / 5.0F)));
    }
}
